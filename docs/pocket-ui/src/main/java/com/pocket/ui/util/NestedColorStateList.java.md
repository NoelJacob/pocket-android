# pocket-ui/src/main/java/com/pocket/ui/util/NestedColorStateList.java

## What this is
A loader that lets color-state-list XML files reference other color-state-list files, which stock Android does not support (a `ColorStateList` maps view states like pressed or checked to colors; `@color/...` references to another selector normally fail to resolve). It flattens nested selectors at load time, merging the outer item's states with each inner item's states, and caches the result.

## How it fits
Themed views load their state colors through `NestedColorStateList.get(context, resId)` instead of `ContextCompat.getColorStateList`. `AppBar` and `TagBadgeView` use it for clickable grey and badge colors. Writers author selectors normally and point items at shared selectors like an amber dark-mode set; at runtime the referenced rules are inlined with the outer states added.

## Key pieces
- `get(Context, resId)`: the single entry point. Returns the cached flattened list when available; falls back to the stock loader for plain single colors; returns null and logs on parse failure. The cache exists because XML parsing on every bind would be expensive.
- `collectColors(into, resources, resId, parentStates)`: recursive XML walk over `<item>` tags. For each item it gathers state attributes (negating `false` ones, matching framework convention), handles `android:alpha` modulation, resolves plain colors directly, and recurses into nested selector references with the accumulated states. Recursion plus state accumulation is what implements the "outer states apply to every inner rule" semantic from the class docs.
- `modulateColorAlpha(baseColor, alphaMod)`: applies an item's `android:alpha` on top of the base color's own alpha, mirroring the framework's `ColorStateList` math.
- `Builder` plus static `cache`: a reused scratch accumulator and a `SparseArray` (a memory-efficient integer-to-object map) result cache. Reusing one builder avoids allocation per load; the cache makes repeat lookups free. Both assume UI-thread use (see notes).

## Junior notes
- MUST be called on the UI thread: the shared `Builder` and cache are unsynchronized. Background use risks corrupting the scratch state.
- The cache never invalidates on configuration change, so configuration-dependent colors would go stale. The code comments flag this; do not add night-mode-dependent colors through this path without addressing it.
- A null return means parsing failed (already logged). Callers should handle null rather than assuming a list always comes back.
