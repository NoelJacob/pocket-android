# Pocket/src/main/java/com/pocket/util/android/view/OnResizeListener.java
## What this is
A single-callback interface that fires whenever a resize-detecting view changes size. It reports the view plus new and old width/height, letting owners react to layout changes (keyboard appearance, rotation, content growth) without polling.
## How it fits
Implemented by consumers of `ResizeDetectView` hosts (`ResizeDetectLinearLayout`, `ResizeDetectRelativeLayout`) and by `BaseWebView`'s resize plumbing; hosts invoke it from `onSizeChanged()`. It is the event contract between size-aware containers and their owners.
## Key pieces
- `onViewSizeChanged(v, newWidth, newHeight, oldWidth, oldHeight)` — WHY: the only event; compare new vs old to detect growth, shrink, or rotation. Usage in words: pass an implementation to `setOnResizeListener()` on a resize-detecting layout.
## Junior notes
- Fires on every size change including the first layout (old sizes 0); guard initialization code with an `oldWidth == 0 && oldHeight == 0` check if needed.
- Called synchronously inside `onSizeChanged` during layout; do not request a new layout unconditionally inside it or you will loop.

