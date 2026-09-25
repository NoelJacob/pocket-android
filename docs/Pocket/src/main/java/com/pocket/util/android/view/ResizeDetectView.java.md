# Pocket/src/main/java/com/pocket/util/android/view/ResizeDetectView.java
## What this is
A one-method interface marking a view as resize-reporting: hosts accept an `OnResizeListener` (a callback fired with new and old sizes) and invoke it from `onSizeChanged()`.
## How it fits
Implemented by `ResizeDetectLinearLayout` and `ResizeDetectRelativeLayout`; consumed by any owner that calls `setOnResizeListener()` to track keyboard, rotation, or content-driven size changes. It is the contract; `OnResizeListener` is the event.
## Key pieces
- `setOnResizeListener(listener)` — WHY: subscribes (or replaces) the size observer. Usage in words: pass your listener after inflation; expect a call on every size change including first layout.
## Junior notes
- Only one listener slot exists; setting a second silently replaces the first, so composite owners must fan out manually.
- Interface lives in the singular file while the callback lives in `OnResizeListener`; do not confuse the two when importing.

