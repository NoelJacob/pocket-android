# pocket-ui/src/main/java/com/pocket/ui/text/PressableSpan.java

## What this is

A one-method interface for a text span (a markup object attached to a slice of text) that wants to know when it is being pressed. The hosting view calls `setPressed(true)` on touch-down inside the span's range and `setPressed(false)` on release, so the span can redraw itself in a highlighted state. It is just the contract; the visual response lives in the implementing span class.

## How it fits

Custom link/highlight spans implement this alongside `ClickableSpan` or `CharacterStyle`, and the touch-handling parent (a `TextView` with a custom movement method, or a view that dispatches touches to spans) toggles the pressed flag during touch handling. `ThemedClickableSpan` in the same package is the sibling concept — a span whose color follows view state — and a press-aware span would combine the two ideas to tint a link while the finger is down.

## Key pieces

- `setPressed(boolean isPressed)` — WHY: the entire contract; pushing press state into the span lets it invalidate/redraw with pressed styling instead of the parent view having to know span internals.

## Junior notes

- Implementing this interface alone does nothing visible — the span must also react (change color/background) and the host view must actually call it during touch dispatch, or presses will never show.
