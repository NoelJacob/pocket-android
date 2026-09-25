# Pocket/src/main/java/com/pocket/sdk/util/view/UpDownAnimator.kt
## What this is
A tiny helper that slides a view vertically off and on screen with a 250ms animation, e.g. hiding a toolbar on scroll down and revealing it on scroll up. It tracks `HIDDEN` vs `SHOWING` so repeated calls are cheap no-ops, and animates `translationY` (a visual offset that moves the view without changing its layout position).
## How it fits
Owned by screens or behaviors with a hide-on-scroll bar or banner; they call `hide()` on scroll down and `show()` on scroll up. The direction (`UP` slides above the screen, `DOWN` below) is fixed at construction, typically matching where the view is docked.
## Key pieces
- `show()` / `hide()` — animate back to offset 0 or out by exactly the view height, flipping state; no-ops when already there. WHY: scroll events fire constantly, so redundant animations must be free.
- `setupAnimator(targetValue)` — one `ObjectAnimator` on `translationY` from the last value to the target, updating `currentValue` each frame. WHY: tracking the live value lets a reversal mid-animation start from the current position instead of jumping.
- `State` (`HIDDEN`, `SHOWING`) — which end position the view is at or moving toward. WHY: the guard that makes repeat calls no-ops.
- `Direction` (`UP`, `DOWN`) — negative or positive height offset. WHY: top-docked views exit upward, bottom-docked ones downward.
## Junior notes
- The hide distance is measured from `view.height` at call time; call `hide()` only after layout (height is 0 before first measure, which would hide to offset 0, i.e. stay visible).
- Overlapping `show()`/`hide()` calls start a new animator without cancelling the old one; for rapid scroll flapping the last call wins visually but both run, so debounce at the call site if it stutters.
