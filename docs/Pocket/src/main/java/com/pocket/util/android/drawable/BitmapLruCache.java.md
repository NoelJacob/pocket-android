# Pocket/src/main/java/com/pocket/util/android/drawable/BitmapLruCache.java
## What this is
An in-memory image cache that holds decoded article images as `CacheableBitmapWrapper` objects, keyed by String. It extends Android's `LruCache` (least-recently-used: when full, the least recently touched entry is evicted first) and measures each entry by real bitmap byte size. It also knows how to drop entries that no screen is showing when memory runs low.
## How it fits
Owned and driven by `com.pocket.sdk.image.ImageCache`, which is the app-wide image pipeline: `ImageCache` puts freshly decoded bitmaps in here via `cache()` and calls `trimMemory()` from low-memory callbacks. Eviction and removal notify each `CacheableBitmapWrapper` through `setCached()`, so the wrapper can recycle its bitmap once nobody displays it. Callers never touch this class directly; they go through `ImageCache`.
## Key pieces
- `BitmapLruCache(maxSize)` — constructor; `maxSize` is the byte budget for all cached bitmaps combined.
- `sizeOf(key, value)` — WHY: tells `LruCache` how big each entry is so eviction is by bytes, not entry count; uses `rowBytes * height`, or 0 for recycled bitmaps.
- `cache(key, value)` — WHY: the only sanctioned way to insert; marks the wrapper as cached before `put()` so its recycle refcount stays correct. Usage in words: call `cache(url, new CacheableBitmapWrapper(bitmap, url))` instead of `put()`.
- `entryRemoved(...)` — WHY: keeps the refcount honest; marks the evicted wrapper as no longer cached, which may recycle its bitmap.
- `trimMemory()` — WHY: emergency relief; iterates a snapshot and removes every entry no view is displaying. Usage in words: call it from `Application.onLowMemory()` (via `ImageCache.trim()`).
## Junior notes
- `LruCache` here comes from `androidx.collection`, not `java.util`; it is thread-safe for get/put.
- Never call `put()` directly on this class; bypassing `cache()` skips the `setCached(true)` bookkeeping and the bitmap may be recycled while cached.
- `snapshot()` returns a copy, so removing inside the `trimMemory()` loop is safe.

