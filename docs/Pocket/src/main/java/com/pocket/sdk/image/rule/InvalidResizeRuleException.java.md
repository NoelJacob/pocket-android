# Pocket/src/main/java/com/pocket/sdk/image/rule/InvalidResizeRuleException.java
## What this is
Checked exception thrown when the resize rule itself is impossible or invalid (e.g. constructed with illegal dimensions), as opposed to the image bytes being bad.
## How it fits
Thrown alongside `InvalidImageException` from `ImageResizer.getResizedBitmap()`; it wraps the underlying `IllegalArgumentException` so background image code sees one declared failure type. `ImageCache` maps it to a failed request the same way.
## Key pieces
- `InvalidResizeRuleException(IllegalArgumentException)` — single wrapping constructor; the cause is preserved via `super(e)`.
## Junior notes
- If you hit this, the bug is in the requested size (what the UI asked for), not the download; check the `fill`/`fit` arguments rather than the network or cache.
