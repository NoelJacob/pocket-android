# pocket-ui/src/main/java/com/pocket/ui/view/badge/BadgeUtil.java

## What this is
A one-method helper that returns the standard badge height in pixels. It takes the larger of the two dimension resources `pkt_badge_height_min` and `pkt_badge_height`, so badges never shrink below their minimum height.

## How it fits
`TextBadgeView.init()` calls `getBadgeSize()` to set its minimum height (via `IntrinsicSizeHelper`) and to compute its vertical padding. Any future badge view that needs the canonical badge height calls this instead of reading the dimens directly.

## Key pieces
- `getBadgeSize(Context)`: reads both badge-height dimens and returns `Math.max(...)` of the two as pixels. Static, so callers need no instance.

## Junior notes
- `getDimensionPixelSize()` converts a dp resource to device pixels; always use it (not `getDimension()`) when you need an int pixel size for layout.
