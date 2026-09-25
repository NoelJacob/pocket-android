# pocket-ui/src/main/java/com/pocket/ui/util/PlaceHolderBuilder.java

## What this is
A factory for article placeholder images: colored tiles with one oversized letter anchored toward a corner, in the style of the Listen design comps. It removes per-screen placeholder drawing code. The deterministic overloads (same id always yields the same color and corner) keep list items visually stable across rebinds and scrolling.

## How it fits
Thumbnail slots, article rows, and collection covers call `getDrawable` when no real image is available. The returned `Drawable` paints a state-aware background plus a large initial-style character, so empty slots still look intentional and themed for light/dark mode.

## Key pieces
- `getDrawable(context, character, color, corner)`: explicit variant for full control over letter, palette, and letter placement.
- `getDrawable(context, String id, character)` / `(context, int id, character)`: deterministic variants that pick color and corner as `id mod palette size`. The string form hashes first. These exist so an article id always maps to the same tile without storing anything.
- `PktColor`: the four palettes (coral, amber, teal, blue), each pairing a background and text color resource. Limited set keeps the list visually coherent instead of random rainbow tiles.
- `Corner`: which corner the oversized glyph leans toward. Varies the composition so adjacent tiles do not look stamped out.
- `PlaceHolderDrawable` (private): the actual drawable. Sizes its font from the bounds in `onBoundsChange`, resolves both colors per state in `updatePaint` so pressed/disabled variants work, draws with the Doyle Medium typeface, and offsets the glyph with `getXTranslate`/`getYTranslate` so the character bleeds toward the chosen corner.

## Junior notes
- The glyph is intentionally oversized relative to its bounds (see the `OUTER_REC_MULTIPLIER` and `FONT_SIZE_MULTIPLIER` constants), so the letter crops at the edges by design, not by a measuring bug.
- Colors are `ColorStateList`s resolved per state, so the placeholder reacts to view state like any themed drawable. But `setAlpha` only forwards to the background paint, so fading the whole tile via alpha leaves the letter fully opaque.
