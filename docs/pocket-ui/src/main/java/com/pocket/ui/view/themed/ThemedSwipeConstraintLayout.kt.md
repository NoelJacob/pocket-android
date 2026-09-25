# pocket-ui/src/main/java/com/pocket/ui/view/themed/ThemedSwipeConstraintLayout.kt
## What this is
A themed ConstraintLayout row that the user can swipe left or right to dismiss or act on, with callbacks as it moves. Think of swiping an item off the screen in a list. It extends ThemedConstraintLayout2, so it is theme-aware like every other Themed container.

## How it fits
Swipeable list rows (e.g. dismissible cards) use this as their row root and set a `swipeListener` for `onSwipedLeft()` / `onSwipedRight()` / `onMovement(fraction)` to react at each stage. `onTouchEvent` tracks the finger: past the movement threshold it follows via translationX animation, on release past `swipeThreshold` it animates off screen, otherwise it springs back via `reset()`. `swipeThresholdPercent` (default half the travel) and `allowSwiping` tune the gesture.

## Key pieces
- `onTouchEvent()` — DOWN records the start; MOVE past MIN_MOVEMENT (25px) drags the view; UP flings off left/right or resets; CANCEL resets.
- `swipeThreshold` — pixel distance that counts as a swipe, derived from the parent width and swipeThresholdPercent.
- `moveOffscreenLeft()` / `moveOffscreenRight()` — animate translationX off screen, then fire the matching listener callback.
- `reset()` / `resetPosition()` — spring back to translationX zero.
- `SwipeListener` — `onSwipedRight()`, `onSwipedLeft()`, and `onMovement(percentToSwipeThreshold)` for progress UI during the drag.
- `MovementUpdateListener` / `OffScreenUpdateListener` — animator callbacks reporting drag fraction and completion.

## Junior notes
- While dragging it calls `requestDisallowInterceptTouchEvent`, so a parent RecyclerView will not steal the gesture mid-swipe.
- `maxSwipeDistance` uses the parent's width, so the row must already be laid out inside its parent for fling distances to be right.
