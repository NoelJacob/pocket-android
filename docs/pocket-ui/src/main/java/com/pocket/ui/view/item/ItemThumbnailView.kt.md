# pocket-ui/src/main/java/com/pocket/ui/view/item/ItemThumbnailView.kt
## What this is
The small rounded-corner thumbnail image in save-list rows and tiles: a 90x60dp image (via `IntrinsicSizeHelper`) with 16dp rounded corners, a grey placeholder box while the real image loads, and an optional play-badge overlay for videos. When there is no image it collapses itself (`GONE_WHEN_EMPTY`) so the row reflows. It extends `ThemedImageView` (an `ImageView` that re-tints with the app theme).
## How it fits
Embedded in `ItemRowView` (right-hand image) and item tiles; `ItemRowView.Binder.thumbnail(...)` sets its drawable and calls `setVideoIndicatorStyle(LIST)` for videos. Image loading goes through `LazyBitmapDrawable` (background-loaded bitmap); while empty, `drawPlaceholder()` paints the grey box. `EmptiableView` plumbing lets parents listen for empty/non-empty transitions to adjust layout.
## Key pieces
- `setVideoIndicatorStyle(VideoIndicator?)` — picks the overlay via `ItemVideoIndicatorDrawable.forItemRow/forItemTile/forDiscoverTile` for `LIST`/`TILE`/`DISCOVER`, or clears it when null. WHY: one enum keeps badge sizing consistent per surface instead of each caller constructing the drawable.
- `onMeasure()` — applies the 90x60dp intrinsic size through `sizeHelper`. WHY: thumbnails keep a stable size even before the image loads, preventing list rows from jumping.
- `onDraw()` — draws the photo (or grey placeholder when drawable is null), then the video badge on top, then rounds the canvas corners. WHY: ordering guarantees the badge is never hidden behind the photo and corners clip both.
- `setImageDrawable()` — forwards to super and updates `emptyHelper` (empty = drawable null, skipped in edit mode). WHY: the empty/collapse behavior tracks exactly what is displayed.
- `CORNER_RADIUS = 16f` — the corner radius in dp. WHY: single constant so rows and tiles share the same rounding.
## Junior notes
- `scaleType` is forced to `CENTER` in `init()`: the image fills the box by cropping, not stretching. Changing scale type will distort or letterbox thumbnails.
- The comment warns `setImageDrawable` does not cover every setter (e.g. `setImageBitmap`/`setImageResource` paths may bypass the empty check) — if you set images another way, update the empty-state call too or the collapse behavior breaks.
