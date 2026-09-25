# Pocket/src/main/java/com/pocket/util/android/view/RecyclingImageView.java
## What this is
An `ImageView` (the standard picture-display widget) that recycles the old bitmap's native memory every time a new bitmap is set. It solves scrolling lists of images slowly leaking native pixel memory by freeing the previous frame as soon as it is replaced.
## How it fits
Used by `com.pocket.util.android.webkit.BaseWebView` as its frozen screenshot view (`mFrozenView`): while the WebView re-renders, a static bitmap snapshot shows, and each new snapshot recycles the last (in words: `mFrozenView = new RecyclingImageView(...)` then repeated `setImageBitmap()`). It produces freed native memory as a side effect of binding.
## Key pieces
- `setImageBitmap(bitmap)` — WHY: the whole feature; looks up the current `BitmapDrawable`, recycles its bitmap, then delegates to super. Usage in words: use exactly like a normal ImageView; replacement cleanup is automatic.
## Junior notes
- Only `setImageBitmap` recycles; `setImageDrawable`/`setImageResource` paths bypass cleanup, so always bind bitmaps through `setImageBitmap`.
- Recycling a bitmap that is still referenced elsewhere (e.g. also in `BitmapLruCache`) corrupts that reference; only use this view for bitmaps it uniquely owns, like screenshots.
- `getDrawable()` is read twice; if a5275 concurrent thread swapped the drawable between reads this could recycle the wrong bitmap, but on the UI thread (main thread) this is safe.

