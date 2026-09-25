# pocket-ui/src/main/java/com/pocket/ui/view/themed/ThemedNestedScrollView.kt
## What this is
A NestedScrollView (a stock scroll container that cooperates with app bars and CoordinatorLayout) that follows Pocket's light/dark theme and supports multiple scroll listeners. For the user it is a normal scrolling area; what it adds over stock is theme-state merging plus a fan-out listener list.

## How it fits
Scrollable screens (and YieldingNestedScrollView, which extends this class) use it as their themed scroll root. Stock NestedScrollView only keeps one scroll listener, so this class keeps its own `scrollListeners` list behind a single real listener: `setOnScrollChangeListener` appends instead of replacing, and `removeOnScrollChangedListener` detaches. Theme merging in `onCreateDrawableState()` keeps state-list backgrounds live.

## Key pieces
- `setOnScrollChangeListener()` — adds to the fan-out list and installs the single delegating `realScrollListener` on the stock view.
- `removeOnScrollChangedListener()` — removes one listener so fragments can avoid leaking callbacks.
- `realScrollListener` — forwards every scroll event to all registered listeners.
- `onCreateDrawableState()` — merges the theme attributes for theme-aware backgrounds.

## Junior notes
- Never call super's listener setter expecting replace semantics here; every call adds, so register once (e.g. in onViewCreated) and remove when done.
- YieldingNestedScrollView inherits the multi-listener behavior automatically.
