# Pocket/src/main/java/com/pocket/util/android/drawable/BitmapUtils.kt
## What this is
A single Kotlin extension function that rasterizes any `Drawable` (any vector or shape graphic) into a `Bitmap` (a grid of pixels). It creates an `ARGB_8888` bitmap sized to the drawable's intrinsic width and height, draws the drawable onto a `Canvas` wrapping that bitmap, and returns it.
## How it fits
Used by `com.pocket.app.reader.internal.originalweb.OriginalWebFragment`, which converts the `ic_pocket_menu` drawable to a bitmap (in words: `ContextCompat.getDrawable(...)!!.toBitmap()`) so it can be handed to an API that only accepts bitmaps. It is a leaf helper with no downstream calls beyond the Android graphics classes.
## Key pieces
- `Drawable.toBitmap()` — WHY: bridges vector/shape drawables to bitmap-only consumers such as share sheets, notifications, or WebView icons. Usage in words: take a loaded drawable and call `.toBitmap()` to get pixels you can pass on.
## Junior notes
- `intrinsicWidth/Height` can be -1 for drawables with no natural size (e.g. solid colors); this helper would crash there, so only use it with real assets like icons.
- `ARGB_8888` means 8 bits per red/green/blue/alpha channel: highest quality, highest memory. Fine for small icons, wasteful for large images.

