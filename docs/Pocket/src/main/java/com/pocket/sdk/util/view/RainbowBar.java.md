# Pocket/src/main/java/com/pocket/sdk/util/view/RainbowBar.java
## What this is
The thin four-color brand strip view seen at the top of Pocket screens and loading states. It owns a `RainbowDrawable`, sizes it to the view, forwards theme (light/dark) state changes, and paints it in `onDraw()`. It exposes the drawable so screens can start and stop its loading animation.
## How it fits
Placed in XML layouts wherever the brand strip or progress indicator belongs; `AbsPocketActivity` and loading screens reference it. State changes flow from the view system through `drawableStateChanged()` into the drawable, and load start/stop flows from callers through `getRainbow().startProgressAnimation()` / `stopProgressAnimation()`.
## Key pieces
- `mRainbow` / `init()` — creates the drawable bound to this view as its animation callback. WHY: animation frames are delivered via `invalidateSelf()` through that callback.
- `getRainbow()` — exposes the drawable for animation control. WHY: the bar itself has no loading state; callers translate their load into start/stop.
- `verifyDrawable(who)` — required override returning true for the rainbow. WHY: without it the view system ignores the drawable's redraw requests and animation freezes.
- `drawableStateChanged()` — forwards the view state (including dark mode) and repaints. WHY: the drawable dims its colors from state alone, with no extra wiring.
- `onSizeChanged()` — sets the drawable bounds to the view size. WHY: the drawable scales its sweep speed from its bounds width.
- `MIN_RAINBOW_HEIGHT` — minimum height from resources. WHY: the strip keeps a consistent thickness everywhere.
## Junior notes
- Forgetting `verifyDrawable` when copying this pattern is the classic bug: the drawable draws once statically but never animates.
- This is a `ThemedView` (a Pocket base view that reacts to theme changes); dark-mode handling inside the drawable depends on state forwarding, so do not remove the `drawableStateChanged()` override.
