# Pocket/src/main/java/com/pocket/util/java/FileLocks.java
## What this is
An in-process replacement for `java.nio` file locks, which on Android are only advisory and do not actually serialize threads. One shared `FileLocks` instance keeps a FIFO queue of `Lock` objects per file path; `lock(path)` blocks until earlier holders release.
For example, two image threads both call `fileLocks.lock(asset.local)` before touching the same cached file, and the second waits until the first calls `Lock.release()`.

## How it fits
Owned by `ImageCache` (`new FileLocks()`, exposed via `getImageFileLocks()`). `ImageCache.exists()`, the download path, and `writeImage()` lock around file checks and writes, and `ImageResizer.waitForLock()` / its bitmap-save path lock through the same instance so resize and download never interleave on one file.

## Key pieces
- `lock(String)` / `lock(File)`: enqueue a `Lock` for that absolute path and block on the previous holder. WHY they exist: the single entry point that serializes all readers/writers of one file.
- `Lock` (inner class): the held permit with `release()` (wakes the next waiter) and `await()`. WHY it exists: models one turn in the per-file queue.
- `releaseQuietly(Lock)`: releases or no-ops on null. WHY it exists: safe cleanup in `finally` blocks without null checks at every call site.
- `mLocks` / `mRecycled`: map of path to waiter queue, plus recycled queue lists. WHY they exist: avoids allocating a new list per lock acquisition.

## Junior notes
- Always `release()` in a `finally` block; a missed release blocks every later waiter on that path forever (until interrupt).
- Locks are per-process only, not cross-process, and reads block writes (no shared read locks yet; the class TODO notes that).
- `lock()` throws `InterruptedException` while waiting, so callers must handle or propagate interruption.
