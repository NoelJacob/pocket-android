# pocket-ui/src/main/java/com/pocket/ui/text/ThemedClickableSpan.java

## What this is

An abstract `ClickableSpan` (a span, i.e. a markup object on a slice of text, that handles taps) whose link color follows the app theme instead of using the platform default blue underline. It holds a `ColorStateList` (an Android color table mapping view states like pressed/disabled to colors) and re-resolves the color on every draw. It also removes the underline that `ClickableSpan` draws by default.

## How it fits

Rich-text labels with inline links (e.g. "Sign up for Pocket" inside a `TextView`) attach an anonymous subclass implementing `onClick(...)` over the link range, passing the theme's link colors and a `StateSource` (usually the host `TextView` itself, exposing `getDrawableState()` — the view's current state set such as pressed/enabled). On each `updateDrawState` the span asks the source for its current state and picks the matching color, so a disabled or pressed container automatically re-tints its links.

## Key pieces

- `StateSource` interface — WHY: decouples the span from any specific view class; anything that can report a drawable state can drive the link color.
- Constructor `(colors, source)` — WHY: injects the theme color table and the state provider together, since the span needs both to resolve a color.
- `updateDrawState(ds)` — WHY: the styling hook Android calls before drawing the span; calls `super`, disables the underline (`setUnderlineText(false)`), and sets the paint color via `colorStateList.getColorForState(...)`, falling back to transparent for unknown states.

## Junior notes

- `ClickableSpan` only fires `onClick` if the `TextView` has a link-aware movement method (e.g. `LinkMovementMethod`); without it the span draws styled but never receives taps.
- The span caches no color — it re-resolves every draw, so changing the host view's state (pressed, enabled) takes effect on the next draw without extra invalidation code.
