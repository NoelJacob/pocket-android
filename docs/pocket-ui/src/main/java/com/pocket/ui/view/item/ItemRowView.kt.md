# pocket-ui/src/main/java/com/pocket/ui/view/item/ItemRowView.kt
## What this is
One row in the Saves/My List feed: the item's text (`ItemMetaView`: title, domain, read time) on the left and an optional rounded thumbnail (`ItemThumbnailView`) on the right. The whole row is checkable (tappable with a selected state for multi-select) and uses databinding (an XML layout wired to fields, here via the generated `ViewItemRowBinding`) instead of `findViewById`.
## How it fits
Inflated by save-list `RecyclerView` adapters on the My List, Home, and Search-results screens — every saved-article row the user scrolls past is one of these. The adapter calls `bind().meta()` to fill the text side and `bind().thumbnail(bitmap, isVideo)` for the image side. It extends `CheckableConstraintLayout` (a `ConstraintLayout` with a checked state for selection) and implements `VisualMargin` for optical list spacing.
## Key pieces
- `Binder.meta(): ItemMetaView.Binder` — hands back the embedded meta view's binder. WHY: callers configure title/domain/time without this class re-declaring every text setter.
- `Binder.thumbnail(LazyBitmap|Drawable, isVideo)` — sets the thumbnail image (`LazyBitmap` = an image that loads in the background; wrapped in `LazyBitmapDrawable`) and toggles the play-badge overlay via `ItemThumbnailView.VideoIndicator.LIST` when `isVideo`. WHY: one call keeps image and video-badge in sync so adapters cannot show a play badge on a photo.
- `Binder.enabled(clicksEnabled, metaEnabled)` / `clear()` — `clear()` resets clicks on, meta on, and empties the thumbnail for recycled rows. WHY: `RecyclerView` reuses row views, so every rebind must start from a known state or old thumbnails bleed through.
- `prepareVisualAscent()` / `prepareVisualDescent()` — trims optical top/bottom margins via `VisualMargin.removeTopMargin`. WHY: thumbnails and text have internal padding that would otherwise make list spacing look uneven.
## Junior notes
- Background is `cl_pkt_touchable_area` (a ripple/pressed-state drawable): the row gets touch feedback for free, but replacing the background removes the ripple.
- `CheckableConstraintLayout` checked state drives selection highlighting — toggling `isChecked` is how multi-select marks rows; do not confuse it with `isEnabled`, which controls dimming/clickability.
