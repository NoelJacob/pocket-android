# Pocket/src/test/java/com/pocket/sdk/image/ImageCacheHelperShould.kt
## What this is
Single test for `ImageCacheHelper.convertToPocketImageCacheUrl`, which rewrites an image URL through Pocket's image-cache proxy. It proves the output is an https URL on `pocket-image-cache.com` whose path embeds the original URL.
## How it fits
Guards production `com.pocket.sdk.image.ImageCacheHelper`, used wherever thumbnails and article images load. Pure JVM test parsing the result with OkHttp's `toHttpUrl()`.
## Key pieces
- `always add some default filters` — converts `http://example.com`, asserts scheme `https`, host `pocket-image-cache.com`, two path segments, original URL preserved; WHY: pins the proxy contract (secure host plus filter path plus original).
## Junior notes
- Path segment count matters: segment 0 holds image filters, segment 1 the encoded original — consumers split on this.
- Only the default-filter case is covered; custom resize/crop params are untested here.
