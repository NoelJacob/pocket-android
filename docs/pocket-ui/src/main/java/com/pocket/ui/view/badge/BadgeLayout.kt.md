# pocket-ui/src/main/java/com/pocket/ui/view/badge/BadgeLayout.kt

## What this is
A single-row container that lays out as many tag/highlight `BadgeView` chips as fit, then shows a "+N" overflow label for the rest. The user sees one neat row of chips (for example on a list item) that never wraps or clips; extra tags collapse into the count. It is a custom `ViewGroup`, so it does its own measuring and positioning.

## How it fits
A hosting screen or list row creates `BadgeView` chips and hands them over via `setBadges()`; `BadgeLayout` measures each chip against the available width during `onMeasure()` and stages only the ones that fit plus the overflow `ThemedTextView`. During `onLayout()` it places those staged children left to right, vertically centered. It sits above `BadgeView` (the chip) and `ThemedViewGroup` (the themed base class that applies the app theme to the container itself).

## Key pieces
- `setBadges(List<BadgeView>)`: replaces the tag list and requests a new measure/layout pass; the actual child views are rebuilt in `onMeasure()`, not here.
- `onMeasure(...)`: the core logic — measures each badge, stages fitting ones with `stage()`, and when one does not fit, backtracks (removing previously staged badges) until the "+N" overflow label fits; a single badge gets the full remaining width so long tag names ellipsize instead of overflowing. Ends by adding staged views with `addViewInLayout` and reporting the row size.
- `stage(view, x)`: records a view and its horizontal offset in a holding list so `onMeasure` can backtrack before committing children.
- `onLayout(...)`: positions committed children at their staged x offsets, centered vertically.
- `LayoutParams` (inner class): a plain `ViewGroup.LayoutParams` with an extra `x` field for the staged offset; `checkLayoutParams`/`generateLayoutParams` enforce it so only this LayoutParams type is accepted.

## Junior notes
- `ThemedViewGroup` is the pocket-ui base class that re-applies theme colors on theme change; custom containers extend it instead of stock `ViewGroup`.
- `onMeasure` here both measures AND chooses children (unusual): it calls `removeAllViews()` and re-adds via `addViewInLayout`, which is the in-layout version of `addView` safe to call during measuring.
- `WRAP_CONTENT` children must be measured with `AT_MOST` specs derived from the parent width; `resolveSize()` then clamps the container's own reported size to the parent's constraints.
