# Pocket/src/main/java/com/pocket/util/android/drawable/CacheableBitmapWrapper.java
## What this is
A reference-counted holder around one Android `Bitmap`. It tracks two counts: how many caches hold it and how many views display it. When both counts drop to zero, it recycles the bitmap (frees its native pixel memory) automatically.
## How it fits
Created wherever a bitmap enters the image pipeline (`com.pocket.sdk.image.Image` / `ImageCache`) and stored inside `BitmapLruCache`. `BitmapLruCache.cache()` calls `setCached(true)` on insert and `setCached(false)` on eviction; image views call `setBeingUsed(true/false)` as they attach and detach. Downstream, `BitmapLruCache.sizeOf()` and `trimMemory()` query `hasValidBitmap()` and `isBeingDisplayed()`.
## Key pieces
- `CacheableBitmapWrapper(bitmap, localPath)` — WHY: pairs the pixels with their cache key for debug logs; throws if bitmap is null so a bad decode fails fast.
- `isBeingDisplayed()` — WHY: tells the cache whether it is safe to evict; true while at least one view shows it.
- `getBitmap()` / `hasValidBitmap()` — WHY: expose the pixels plus a recycled check, so cache sizing never measures a dead bitmap.
- `setCached(added)` — WHY: cache-side refcount, called by `BitmapLruCache`, not by app code directly.
- `setBeingUsed(beingUsed)` — WHY: view-side refcount; views signal attach/detach here. Usage in words: call `setBeingUsed(true)` when binding the bitmap to a view, `false` when unbinding.
- `checkState()` — WHY: the actual reaper; recycles the bitmap the moment both counts hit zero.
## Junior notes
- `Bitmap.recycle()` is terminal: any later draw of that bitmap throws. The two counters exist exactly to prevent recycling a bitmap still on screen.
- `setCached` is package-private, so only classes in this package (practically `BitmapLruCache`) should call it; views use `setBeingUsed`.
- Counters are plain ints with no synchronization; all calls are expected on the image pipeline's threads.

