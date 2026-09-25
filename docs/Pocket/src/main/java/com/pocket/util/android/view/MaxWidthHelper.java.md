# Pocket/src/main/java/com/pocket/util/android/view/MaxWidthHelper.java
## What this is
A composable delegate that adds `maxWidth` support to any custom view. A view owns an instance, reads the `maxWidth` XML attribute through it, and routes `onMeasure()` through it so wide screens (tablets, landscape) get a capped, readable column instead of full-bleed rows.
## How it fits
Embedded in `ResizeDetectLinearLayout` and `ResizeDetectRelativeLayout`, which forward their width measure specs through `onMeasure()` and expose `getMaxWidth()/setMaxWidth()` via the `MaxWidthView` interface. It reads `R.styleable.MaxWidthView_maxWidth` and produces an adjusted width measure spec.
## Key pieces
- `MaxWidthHelper(...)` constructors — WHY: four overloads matching view constructor styles; the fuller ones resolve the attribute against theme defaults. Usage in words: construct alongside the host view with the same attrs.
- `getMaxWidth()` / `setMaxWidth(maxWidth)` — WHY: runtime read/write of the cap in pixels.
- `onMeasure(widthMeasureSpec)` — WHY: the actual clamp; returns a narrowed spec when `0 < maxWidth < measured`. Usage in words: in the host's `onMeasure`, reassign `widthMeasureSpec = helper.onMeasure(widthMeasureSpec)` before calling super.
- `MaxWidthView` — WHY: opt-in interface so generic code can set max width on either layout.
## Junior notes
- Zero means "no cap"; negative values are also treated as uncapped because the `maxWidth > 0` guard fails.
- The helper preserves the original measure mode (EXACTLY/AT_MOST); it only shrinks the size, never loosens the mode.

