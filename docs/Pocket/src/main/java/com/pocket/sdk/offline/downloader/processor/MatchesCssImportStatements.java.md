# Pocket/src/main/java/com/pocket/sdk/offline/downloader/processor/MatchesCssImportStatements.java

## What this is
The streaming matcher that finds `@import`ed stylesheets inside CSS files, in all three spellings (`@import "a.css"`, `@import url('a.css')`, `@import a.css`). Each hit becomes a `STYLESHEET`-typed capture so nested CSS is fetched and processed recursively.

## How it fits
Used by `StreamingMarkupProcessor` when processing stylesheet assets discovered during a `WebDownloader` run. Hits go to `AssetHandler.forPageAsset(...)` (base URL = the importing CSS file) and then to `AssetDownloader`, which fetches the imported CSS and scans it in turn — this is how `@import` chains resolve offline.

## Key pieces
- `type()` returning `Asset.STYLESHEET` — marks captures for recursive CSS processing. WHY: unlike images, stylesheets can contain further imports.
- `Step` machine + `read(codepoint)` — char-by-char equivalent of the quoted `@import` regex. WHY: the processor streams, so no whole-file regex is possible.
- `isValidCaptureChar()` — rejects parens inside the captured URL. WHY: keeps `url(...)` framing from leaking into the asset path.

## Junior notes
- The class javadoc's original regex is the spec; if you change matching behavior, update the comment and vice versa.
- `@import` URLs are relative to the CSS file, not the page — never resolve them against the page URL or nested imports break.
- Instances are reused: `reset()` must restore `step` and `index` or state leaks between files.
