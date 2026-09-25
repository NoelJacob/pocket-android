# Pocket/src/main/java/com/pocket/sdk/image/rule/InvalidImageException.java
## What this is
Checked exception (an `Exception` the compiler forces callers to handle) thrown when a file decodes to a zero or negative width/height, meaning the bytes are not a usable image.
## How it fits
Thrown by `ImageResizer.getResizedBitmap()` during decode; `ImageCache` treats that request as failed and flags the asset `.nf` so the corrupt URL is not retried forever. The `(outWidth, outHeight)` constructor formats the useless dimensions into the message for logs.
## Key pieces
- `InvalidImageException(String)` / `InvalidImageException(int, int)` — message-only and dimension-reporting constructors; no extra behavior.
## Junior notes
- Checked vs unchecked matters here: `IOException` and this type must be caught or declared, while `getResizedBitmapQuietly()` exists for callers that want null instead of try/catch.
