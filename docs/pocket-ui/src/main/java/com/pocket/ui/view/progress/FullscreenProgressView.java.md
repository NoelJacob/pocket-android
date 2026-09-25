# pocket-ui/src/main/java/com/pocket/ui/view/progress/FullscreenProgressView.java
## What this is
A fullscreen dimmed overlay (semi-transparent black) that blocks all touch input while the app is busy. It shows an optional spinning RainbowProgressCircleView and an optional status message on top of the dim. It starts hidden; callers flip it visible while work runs and hide it after.

## How it fits
Screens include it in their layout (view_fullscreen_progress) above all other content and drive it through `bind()`: `visible(true/false)` to show or hide, `message(...)` for status text, `progressCircle(...)` to toggle the spinner. It extends ThemedConstraintLayout so it re-themes with the rest of the screen. The Binder's `clear()` resets it to hidden with no message.

## Key pieces
- `FullscreenProgressView` — the overlay itself; `init()` inflates the layout, makes itself clickable/focusable (so touches behind it are swallowed), and hides itself by default.
- `bind()` / inner `Binder` — the only API callers use: `visible()`, `message()` (hides the TextView when null via `setTextOrHide`), `progressCircle()`, and `clear()` to reset everything.
- `progressCircle` and `messageView` — lookups of `R.id.progress_circle` and `R.id.message` from the inflated layout.

## Junior notes
- `setClickable(true)` on a full-screen view is what blocks touches reaching views underneath; without it taps would fall through.
- The `Binder` pattern used across pocket-ui is a small fluent helper owned by the view so callers configure it without touching child views directly.
