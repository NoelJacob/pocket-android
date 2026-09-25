# pocket-ui/src/main/java/com/pocket/ui/view/notification/PktSwipeDismissBehavior.java

## What this is
The drag-to-dismiss engine behind Pocket's banners: attach it to a card inside a `CoordinatorLayout` and the user can fling or drag the card sideways off screen to dismiss it. It is a fixed copy of Material's `SwipeDismissBehavior` that keeps the card visibly following the finger while swiping (the stock version had a broken slide animation).

## How it fits
Set as the `CoordinatorLayout.Behavior` on the swiped child — `PktSnackbar.userDismissable()` attaches it to the snackbar root, `ItemSnackbarView.init()` to its card, both with `SWIPE_DIRECTION_ANY`. Drag progress is driven by an internal `ViewDragHelper`; on release past the threshold (or on fling) it animates the card off screen and calls `OnDismissListener.onDismiss`, otherwise it settles back. `onDragStateChanged` reports drag/settle phases.

## Key pieces
- `SWIPE_DIRECTION_START_TO_END` / `END_TO_START` / `ANY` — allowed swipe directions, RTL-aware (start/end flip in right-to-left locales); Pocket's banners use ANY.
- `setListener(OnDismissListener)` — `onDismiss(view)` fires once the card is swiped off; `onDragStateChanged(state)` mirrors `ViewDragHelper` IDLE/DRAGGING/SETTLING.
- `setDragDismissDistance(float)` — fraction of the width (0–1, clamped, default 0.5) the card must be dragged to count as a dismiss when released without fling velocity.
- `setSensitivity(float)` — drag-start sensitivity multiplier; only takes effect before first touch handling creates the helper.
- `onInterceptTouchEvent` / `onTouchEvent` — ignores touches starting outside the child (`mIgnoreEvents`) and delegates the rest to `ViewDragHelper`.
- `mDragCallback` — the core: `tryCaptureView` captures any touch on the child; `clampViewPositionHorizontal` locks movement to the allowed direction(s) and one width each way; `clampViewPositionVertical` freezes vertical movement; `shouldDismiss` decides fling (any velocity in an allowed direction dismisses) vs drag (distance past threshold); `onViewReleased` settles off-screen or snaps back.
- `SettleRunnable` — re-posts itself each animation frame until settling finishes, then fires `onDismiss` for real dismissals.
- `getDragState()` — current IDLE/DRAGGING/SETTLING state (IDLE when untouched).

## Junior notes
- This must be installed via the child's `CoordinatorLayout.LayoutParams.setBehavior` — as a `CoordinatorLayout.Behavior` it only receives touches when the parent is a `CoordinatorLayout`.
- A fling with any nonzero velocity in an allowed direction always dismisses, even a tiny one — only a slow release falls back to the distance threshold, which catches newcomers off guard when testing.
