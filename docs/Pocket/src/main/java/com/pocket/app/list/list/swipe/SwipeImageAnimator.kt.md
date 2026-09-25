# Pocket/src/main/java/com/pocket/app/list/list/swipe/SwipeImageAnimator.kt

## What this is
A stateless math helper that grows and fades the archive icon while the user drags a list row left or right, giving the swipe a stretchy, accelerating feel.

## How it fits
Called from `MyListAdapter`'s `SwipeListener.onMovement(percentToSwipeThreshold)` on every drag frame, with the left and right icon views. It shows only the icon on the drag side and hides the other.

## Key pieces
- `updateImage(swipeThresholdPercent, leftImage, rightImage)` — WHY: the single entry point; sign picks the side, magnitude drives scale/alpha.
- `y = 12x^4` curve — WHY: slow start with a sharp rise near the threshold, so the icon swells just as the swipe is about to commit; capped at 2x scale and 1.0 alpha.

## Junior notes
- Positive percent means swiping right (left icon shows) — the sign convention comes from the swipe layout, not from here.
- This is a pure `object` (Kotlin singleton) with no state, so it is safe to call at 60fps from any row; keep it allocation-free.
