# pocket-ui/src/main/java/com/pocket/ui/view/themed/ThemedRecyclerView.java
## What this is
A RecyclerView (a stock scrollable list that reuses row views for performance) that follows Pocket's light/dark theme. No list behavior changes; theme-aware row and background colors just update on theme switch.

## How it fits
SkeletonList extends it for placeholder lists, and real item lists use it as their themed list base. Its only addition over stock is merging the theme attributes from AppThemeUtil in `onCreateDrawableState()`, so state-list drawables in rows re-resolve.

## Key pieces
- `onCreateDrawableState()` — merges `AppThemeUtil.getState(this)` into the drawable state; the whole reason the class exists.

## Junior notes
- Theme state on the RecyclerView propagates to stateful row backgrounds during drawable-state passes; row views do not each need to be Themed.
- All three standard View constructors are present so it works from code and XML inflation.
