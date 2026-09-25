# Pocket/src/main/java/com/pocket/util/java/PktFileUtils.kt
## What this is
Two file utilities: `unzip(path)` extracts a zip next to itself while rejecting zip-path-traversal attacks, and `createFile(...)` creates a file plus its parent directories with a retry workaround for flaky `mkdirs()` on some devices. Both are `@JvmStatic` so Java callers use them as statics.
For example, `PktFileUtils.unzip(diskPath + "/" + filename)` unpacks a downloaded font pack, and `createFile(asset.local.absolutePath)` prepares an image-cache destination.

## How it fits
`PremiumFonts` unzips downloaded font packs via `unzip()` and checks the boolean result before registering fonts. `ImageCache.writeImage()` prepares its target with `createFile()` before streaming bytes through Okio. Failures return false or throw `IOException` for the caller to handle.

## Key pieces
- `unzip(path: String): Boolean`: streams each `ZipEntry` beside the archive, creating directories as needed. WHY it exists: dependency-free extraction for downloaded packs.
- Traversal guard inside `unzip` (canonical path must start with the destination dir): WHY it exists: blocks malicious zips containing `../../` entries, per the Google zip-security guidance linked in the code.
- `createFile(path)` / `createFile(file, createEmptyFile)`: `mkdirs()` parents with a retry loop, optionally creating the empty file. WHY it exists: works around observed transient `mkdirs()` failures on Android; inherited from the old `RilFileWriter` downloader.

## Junior notes
- `unzip` catches all exceptions, logs, and returns false; always check the return value instead of assuming files appeared.
- The retry loop in `createFile` is a deliberate hack kept until proven unnecessary; do not "simplify" it away without device testing.
- A `canonicalPath` resolves `.`, `..`, and symlinks to the true location, which is what makes the traversal check sound.
