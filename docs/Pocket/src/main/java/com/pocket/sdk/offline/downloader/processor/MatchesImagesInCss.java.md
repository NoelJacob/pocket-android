# Pocket/src/main/java/com/pocket/sdk/offline/downloader/processor/MatchesImagesInCss.java

## What this is
The streaming matcher that finds images referenced from CSS, e.g. `background-image: url("/awesome/image.jpg")`. Each hit becomes an `IMAGE`-typed capture whose URL is downloaded and whose markup text is rewritten to the local relative path.

## How it fits
Runs inside `StreamingMarkupProcessor` when processing both HTML `<style>` blocks and standalone stylesheet assets during a `WebDownloader` run. Hits flow to `AssetHandler.capture()` (rewrite) and `AssetDownloader.download()` (fetch) with the containing CSS file as the base for relative URLs.

## Key pieces
- `type()` returning `Asset.IMAGE` — marks captures as leaf assets. WHY: images need downloading but never recursive processing.
- `Step` machine (`OPEN` → background/url → capture …) + `read(codepoint)` — char-by-char equivalent of the quoted `background...url(...)` regex. WHY: streaming input forbids whole-file regex.
- `advance()` / `advanceIndex()` / `completed()` / `nope()` / `continuing()` — step transitions and accept/reject outcomes. WHY: keeps the automaton readable as named moves instead of inline index math.

## Junior notes
- Only `url(...)`-style references match here; `<img src>` in HTML is `MatchesImagesInHtml`'s job. A missing offline image means checking which of the two missed it.
- Quoted, single-quoted, and unquoted URLs must all keep working — cover all three if you touch the quote handling.
- Matcher instances are reused across files; `reset()` must clear `step`/`index`.
