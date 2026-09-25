# Pocket/src/main/java/com/pocket/sdk/offline/downloader/processor/StreamingMarkupProcessor.java

## What this is
The low-memory engine that reads page HTML or CSS as a stream, finds image/CSS links via small char-by-char matchers, rewrites each link to its future local relative path, and writes the modified file to disk — never holding the whole page in memory. It replaces two older regex-over-full-string implementations that could use megabytes per page on phones.

## How it fits
`WebDownloader` constructs one per page/stylesheet (`sourceUrl`, temp dir, output file, size cap, `AssetHandler`) and calls `processHtml(encoding, src)` / the CSS path. The five matchers (`MatchesImagesInHtml`, `MatchesStylesheetLinks`, `MatchesImagesInCss`, `MatchesCssImportStatements`, `MatchesCleanup`) feed captures to the `LiteralHandler`; discovered assets go to `AssetDownloader` while rewritten bytes stream into `tmp2` and are atomically moved to the item's `web.html` (or asset file) on success.

## Key pieces
- `processHtml(encoding, src)` — charset detect (via `UniversalDetector`, a library that guesses text encoding) into `tmp1` when unknown, then streaming process into `tmp2`, then publish. WHY: encoding must be known before decoding chars, but the page must still never be fully materialized as a string.
- `process(reader, matchers...)` — the core loop: byte-by-byte read, per-matcher `read(codepoint)`, buffer window for in-flight captures, write-through of settled output. WHY: single pass with an ~8KB buffer is the memory win.
- `Matcher` interface (`type()`, `read()`, `reset()`, `continuing()`) + `Mode` (`MATCHING`/`CAPTURING`) + `starts`/`ends`/`capturing` maps — the contract and bookkeeping for overlapping matchers. WHY: several patterns scan the same stream concurrently; only captures become replacements.
- `LiteralHandler` interface — receives each captured URL, returns replacement text. WHY: separates finding (`Matcher`) from path mapping (`AssetHandler`).
- `Result` / `HtmlSuccess` + `maxSize` + `tmp1`/`tmp2` + `PendingCleanup` — outcome, oversize abort (returns failure instead of downloading), temp files named by URL hash, and single-use guard (`used` flag). WHY: huge pages fail cleanly and instances cannot be accidentally reused.
- `PktFileUtils` + okio (`Buffer`, `BufferedSource/Sink`) — file creation and buffered byte plumbing. WHY: okio (a Java I/O library) gives cheap buffered streaming without large heaps.

## Junior notes
- Memory is the whole point: do not add code that accumulates the page (e.g. collecting all captures into a list of strings is fine; holding full markup is not). `DEBUG_CAPTURES` explicitly defeats this and must stay off.
- Charset fallback is UTF-8 after detection failure; mojibake (garbled text from wrong encoding) in offline pages usually traces to the detect-then-decode path here.
- Temp files embed the URL's SHA-256 hash; concurrent downloads of the same URL can collide (noted TODO) — do not assume per-instance isolation on disk.
