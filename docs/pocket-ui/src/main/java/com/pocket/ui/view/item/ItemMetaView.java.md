# pocket-ui/src/main/java/com/pocket/ui/view/item/ItemMetaView.java
## What this is
The text half of a save-list row: the item's title in large type, the source domain (e.g. "nytimes.com") and estimated reading time ("· 5 min") beneath it, plus an optional small indicator icon. The user sees the title truncated to a capped number of lines. Title/domain/time/indicator are all set through a chainable `bind()` helper.
## How it fits
Embedded in `ItemRowView` (`R.id.meta` in `view_item_row`) and bound via `ItemRowView.Binder.meta()`, which returns this view's `Binder` directly. List adapters in the Saves/My List screens call `title(...)`, `domain(...)`, `timeEstimate(...)`, and `indicator(...)` per row. It extends `VisualMarginConstraintLayout` and inflates `R.layout.view_item_meta` (`title`, `domain`, `time_estimate`, `indicator`).
## Key pieces
- `Binder.title(CharSequence)` / `titleMaxLines(int)` (default 10, reset by `clear()`) — sets the headline and its line cap. WHY: different list styles (compact vs. excerpt) reuse the same view with different caps.
- `Binder.domain(CharSequence)` — sets the publisher/source line. WHY: separate field so adapters can null it for items without a domain without touching the title.
- `Binder.timeEstimate(CharSequence)` — shows " · X min" or hides the view when empty. WHY: prefixing the separator here keeps every caller from duplicating the " · " formatting and the empty-state hiding.
- `Binder.indicator(Drawable)` — shows/hides the small status icon (`GONE` when null). WHY: optional per-row affordance (e.g. offline or collection state) without reserving space when unused.
- `setEnabled(boolean)` — propagates enabled/disabled down to all children via `EnabledUtil.setChildrenEnabled`. WHY: dimming the whole meta block (read/archived states) in one call instead of per-field.
## Junior notes
- `title` uses `TruncateAt.END` (ellipsis "…"), so long titles clip with "…" rather than wrapping forever — the `titleMaxLines` cap and ellipsis work together.
- This view's `Binder` is an inner class: do not hold it past the view's lifetime, and in a `RecyclerView` always call `clear()` (or set every field) on rebind or stale titles leak into recycled rows.
