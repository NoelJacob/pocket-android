# pocket-ui/src/main/java/com/pocket/ui/view/notification/ItemSnackbarView.java

## What this is
A rich "item saved" banner: a rounded card showing the saved item's thumbnail, a small status icon, a feature title (e.g. "Saved"), and the item's metadata (title, excerpt, domain). The whole card is tappable and can be swiped away in any direction.

## How it fits
Inflated from `R.layout.view_item_snackbar` and shown by feature screens after actions like saving an item, as an alternative to the text-only `PktSnackbar`. Hosts drive it through `bind()`: set the thumbnail, icon, titles, tap handler, and swipe-dismiss listener. The card inside is wired to `PktSwipeDismissBehavior` (swipe in any direction dismisses); taps go through the inner `CardView`, whose elevation, radius, and themed background are set up in `init()`.

## Key pieces
- `ItemSnackbarView` — extends `CoordinatorLayout` (required for the swipe behavior to work); `init()` inflates the layout, styles the card, attaches the dismiss behavior to the card, and disables padding clipping so the card shadow is not cut.
- `bind()` / `Binder` — fluent API; `clear()` empties thumbnail/icon/title, nulls tap and dismiss handlers, and resets the meta block to single-line titles.
- `Binder.thumbnail(LazyBitmap)` — item artwork, wrapped in a `LazyBitmapDrawable` (Pocket's lazily-decoded image wrapper); null clears it.
- `Binder.icon(@DrawableRes)` — small status icon; 0 hides the view.
- `Binder.featureTitle` — the bold banner line (e.g. what just happened).
- `Binder.meta()` — returns the inner `ItemMetaView.Binder` for the item's title/excerpt/domain lines.
- `Binder.onClick` — tap handler on the card.
- `Binder.onDismiss` — swipe-dismiss listener, forwarded to the `PktSwipeDismissBehavior`.

## Junior notes
- The swipe behavior must be attached via `CoordinatorLayout.LayoutParams.setBehavior` on the swiped child (the card) — putting it on the root silently does nothing.
- `setClipToPadding(false)` on the root lets the card's shadow and swipe translation paint outside the view bounds instead of being clipped.
