# Pocket/src/main/java/com/pocket/sdk/image/ImageCacheHelper.kt
## What this is
One small helper that rewrites any image URL into a `pocket-image-cache.com` URL with server-side filters (`format(jpeg)`, `quality(60)`, `no_upscale()`, `strip_exif()`). This shrinks downloads and normalizes the format before the bytes ever reach the phone.
## How it fits
Called by `ImageCache` when building the HTTP request for an image download. The returned URL is what `EclecticHttp` actually fetches; the original URL is kept as the `Asset` identity while the cached bytes are the converted JPEG. It is a Kotlin `object` (a singleton with no constructor) with a `@JvmStatic` method so Java callers like `ImageCache` can call it as a static.
## Key pieces
- `convertToPocketImageCacheUrl(url)` — builds `https://pocket-image-cache.com/filters:format(jpeg):quality(60):no_upscale():strip_exif()/<original-url>` using OkHttp's `HttpUrl.Builder`.
## Junior notes
- `addPathSegment(url)` percent-encodes the original URL as one path segment; the whole original URL (including `https://`) ends up embedded in the path, which is what the cache service expects.
