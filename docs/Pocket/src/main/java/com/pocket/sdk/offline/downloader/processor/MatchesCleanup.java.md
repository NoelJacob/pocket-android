# Pocket/src/main/java/com/pocket/sdk/offline/downloader/processor/MatchesCleanup.java

## What this is
The last-resort streaming matcher: it catches absolute `http(s)` links in `src=`/`background=` attributes that the specific image/stylesheet matchers missed, so no remote URL survives into saved offline HTML. It reports hits as stylesheet-typed captures for generic handling.

## How it fits
Runs inside `StreamingMarkupProcessor.processHtml()` alongside the four specific matchers (`MatchesImagesInHtml`, `MatchesStylesheetLinks`, `MatchesImagesInCss`, `MatchesCssImportStatements`). Order matters: specific matchers claim their patterns first; this one sweeps leftovers. Each hit flows to `AssetHandler.capture()` for rewriting and to `AssetDownloader` for fetching.

## Key pieces
- `type()` returning `Asset.STYLESHEET` — generic handling bucket. WHY: leftovers are heterogeneous, so they share the catch-all path rather than pretending a precise kind.
- `Step` state machine (`OPEN` → `HTTP`/`URL` …) + `read(codepoint)` — incremental matcher over the character stream. WHY: streaming forbids regex over the whole page, so each pattern is a hand-rolled char-by-char automaton.
- `Mode.MATCHING` vs `Mode.CAPTURING` — whether the current chars are pattern framing or the URL to extract. WHY: only `CAPTURING` chars become the replacement span.

## Junior notes
- This is a sweep, not a parser: it can over-match odd markup. If a page shows broken links offline, check here last after the specific matchers.
- The original regex is quoted in the class javadoc — use it as the spec when touching the state machine, and keep the two in sync.
- `reset()` must clear `step`/`index` between runs; matcher instances are reused across pages.
