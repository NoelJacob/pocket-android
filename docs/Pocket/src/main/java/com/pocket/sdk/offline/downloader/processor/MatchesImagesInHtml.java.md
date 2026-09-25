# Pocket/src/main/java/com/pocket/sdk/offline/downloader/processor/MatchesImagesInHtml.java

## What this is
The streaming matcher that finds images in HTML tags: `<img>`, `<input>`, `<table>`, `<td>` `src=`/`background=` attributes (e.g. `<img src="something.jpg">`). Each hit becomes an `IMAGE`-typed capture for download and link rewriting.

## How it fits
The highest-volume matcher inside `StreamingMarkupProcessor.processHtml()` during a `WebDownloader` page fetch. Hits go to `AssetHandler.forWebPage(...).capture()` so page-relative URLs resolve against the article URL, then to `AssetDownloader` for fetching while the rewritten `<img src="relative/path">` is written into the saved `web.html`.

## Key pieces
- `type()` returning `Asset.IMAGE` — leaf-asset marking. WHY: HTML images are endpoints, never recursed into.
- `Step` machine + `read(codepoint)` + `skipping(codepoint)` — tag/attribute scanner equivalent of the quoted `<img...src=...>` regex. WHY: must skip arbitrary attributes (`alt`, `class`, newlines) and still land on `src`/`background`.
- `advance()` / `completed()` / `nope()` / `advanceIndex()` — automaton transitions. WHY: shared vocabulary with the sibling matchers for accept/reject.

## Junior notes
- Only the four listed tags are scanned; images on other tags (`<video poster>`, `<source srcset>`, inline `style=`) are intentionally out of scope and fall to `MatchesCleanup` or stay remote. Extend the tag list deliberately, not by accident.
- Attribute order and quoting vary wildly in the wild (`src='x'`, `SRC = "x"` handling per implementation); test with reordered/extra attributes when changing this machine.
- Instances are reused: `reset()` must clear tag/attribute progress between pages.
