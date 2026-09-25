# Pocket/src/main/java/com/pocket/util/android/drawable/ShadowUtil.java
## What this is
A guard against a historic Android native crash when a text or paint shadow radius exceeds 25 pixels. It clamps any requested radius down to `MAX_RADIUS` (25) before applying it.
## How it fits
Used by `com.pocket.app.reader.internal.article.image.ImageViewerActivity`, which captions fullscreen images (in words: `ShadowUtil.setShadowLayer(caption, 8f, 0, -1, black)`) so caption text stays readable over photos. It wraps `TextView.setShadowLayer` and `Paint.setShadowLayer` and produces nothing downstream.
## Key pieces
- `MAX_RADIUS` — WHY: the platform-safe ceiling; values above 25px crashed native code per the linked Android issue.
- `getSafeRadius(radius)` — WHY: pure clamp (`min(25, radius)`); use it when computing a radius yourself.
- `setShadowLayer(textView, ...)` / `setShadowLayer(paint, ...)` — WHY: drop-in replacements for the framework calls with clamping built in. Usage in words: call these instead of `view.setShadowLayer(...)` whenever the radius comes from data or dimens.
## Junior notes
- Shadow radius units are pixels, not dp; an 8dp radius on an xxhdpi screen is 24px, close to the cap, so always go through this helper.
- Negative dx/dy just offsets the shadow up/left; here `dy = -1` lifts the shadow slightly above the text for a halo-over-photo effect.

