# pocket-ui/src/main/java/com/pocket/ui/view/scroll/YieldingNestedScrollView.java
## What this is
A scroll container that behaves like a normal NestedScrollView when its content overflows, but lets touches pass through to views behind it when everything already fits on screen. Visually it is just a scroll view; the difference is only in touch handling.

## How it fits
Used for bottom sheets and overlays layered over interactive content: when the sheet's content is short, taps outside the content fall through instead of being swallowed. `onLayout` records whether the single child is larger than the viewport (`isScrollable`); `onInterceptTouchEvent` only intercepts when scrolling is actually possible. It extends ThemedNestedScrollView so theming is unchanged.

## Key pieces
- `onLayout()` — recomputes `isScrollable` from child versus viewport size (including padding) on every layout.
- `onInterceptTouchEvent()` — returns false (yield) when not scrollable, otherwise defers to the normal scroll behavior.

## Junior notes
- Returning false from onInterceptTouchEvent does not disable the view; children still get touches, the container just never steals the gesture to scroll.
- The check runs in onLayout, so rotation or content changes automatically flip the behavior on the next frame.
