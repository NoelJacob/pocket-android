# Pocket/src/main/java/com/pocket/util/android/view/MaxHeightScrollView.java
## What this is
A theme-aware `NestedScrollView` (a scrollable container) that never grows taller than a `maxHeight` attribute. Short content wraps normally; tall content scrolls inside the cap instead of pushing other UI off screen.
## How it fits
Inflated by `res/layout/activity_item_tagging.xml` for the tag editor sheet, keeping the tag list from covering the whole screen. It reads `PocketTheme_maxHeight` from XML in `initAttrs()` and enforces it in `onMeasure()` (the layout pass that decides view sizes). It produces a capped-height scroll container.
## Key pieces
- `initAttrs(attrs)` — WHY: pulls `maxHeight` from the `PocketTheme` styleable so designers set it in XML.
- `setMaxHeight(px)` — WHY: runtime cap changes with relayout. Usage in words: pass pixels to shrink or grow the cap after inflation.
- `onMeasure(...)` — WHY: the clamp itself; rebuilds the height spec with `min(measured, maxHeight)` before measuring children.
## Junior notes
- Units are pixels everywhere; XML dimens are already pixels by the time they arrive, but code callers must convert dp themselves.
- The no-attrs constructor leaves `maxHeight` at 0, which clamps height to zero; always use the XML constructors or call `setMaxHeight()` when constructing in code.
- `setMaxHeight` calls both `requestLayout()` and `invalidate()`; either alone would suffice for a size change, but the pair is harmless.

