# pocket-ui/src/main/java/com/pocket/ui/view/info/PageIndicatorView.java
## What this is
A horizontal row of small dots showing pager position: the current page's dot is highlighted (selected state) and the rest are dim. It is pure display — it does not handle swipes itself; a pager tells it which dot is current. It extends `ThemedLinearLayout`, so dot colors follow the app's light/dark theme.
## How it fits
Always paired with `PageIndicatedViewPager`, which calls `bind().pageCount(n)` when an adapter is installed and `bind().currentIndex(i)` from its internal page-change listener on every swipe. It can also be used standalone anywhere a manual dot strip is needed. Each dot is an `ImageView` holding a `PageIndicatorDrawable` tinted by `R.color.pkt_page_indicator`.
## Key pieces
- `Binder.pageCount(int)` — clears all dots and creates exactly `value` dot `ImageView`s with 6dp side margins. WHY: rebuilding on count change is simpler and safer than diffing, and page counts are tiny.
- `Binder.currentIndex(int)` — deselects the old dot and selects the new one via `safeSetChildSelected`. WHY: selection state (not swapping drawables) is what flips the dot color through the theme color-state list.
- `safeSetChildSelected(index, selected)` — bounds-checked `setSelected()` that no-ops on out-of-range indices. WHY: protects against race where the pager reports a position before dots are rebuilt.
- `PageIndicatorDrawable` — 3.5dp-radius circle drawable whose color comes from `NestedColorStateList` for `pkt_page_indicator` (different color per selected/unselected state). WHY: a custom `Drawable` keeps dots resolution-independent and theme-aware without shipping bitmap assets.
## Junior notes
- This is a stateful drawable (`isStateful()` returns true): color changes only happen because the parent `ImageView`'s selected state propagates to the drawable. Setting the color directly on the `Paint` would break theming.
- Dots are plain `ImageView`s with no click handling — tapping a dot does nothing. If a design needs tap-to-jump, that must be added; do not assume it works.
