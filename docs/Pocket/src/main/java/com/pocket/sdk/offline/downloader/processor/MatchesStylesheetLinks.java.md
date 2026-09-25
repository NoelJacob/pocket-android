# Pocket/src/main/java/com/pocket/sdk/offline/downloader/processor/MatchesStylesheetLinks.java

## What this is
The streaming matcher that finds stylesheets linked from HTML (`<link ... href="yay.css">`). It only fires when the tag also contains the word "stylesheet", so icon/shortcut links are ignored. Each hit becomes a `STYLESHEET`-typed capture for recursive download.

## How it fits
Runs in `StreamingMarkupProcessor.processHtml()` on page HTML during `WebDownloader` runs. Hits resolve against the page URL via `AssetHandler.forWebPage(...)`, fetch through `AssetDownloader`, and the imported CSS is itself streamed for `@import`s and `url(...)` images — the top of the recursion chain.

## Key pieces
- `type()` returning `Asset.STYLESHEET` — recursion marking. WHY: linked CSS must itself be scanned for more assets.
- `Step` machine + `read(codepoint)` — char-by-char equivalent of the quoted `<link...href=...>` regex. WHY: streaming forbids whole-tag regex.
- `lookForStylesheetWord()` + `foundStyleSheet` flag — the extra "stylesheet" gate with its own `stylesheetIndex` cursor. WHY: `<link rel="icon" href=...>` must not be downloaded as CSS.
- `advance*()` / `completed()` / `nope()` transitions — accept/reject plumbing shared with sibling matchers. WHY: consistent automaton structure across the package.

## Junior notes
- The "stylesheet" word can appear before or after `href` in the tag; the dual-cursor design handles both orders. Do not simplify to "href only".
- Matching is case-sensitive in practice for the tag/attribute names as implemented — check `read()` before assuming `<LINK>` works.
- Reused across pages: `reset()` must clear `step`, `index`, `stylesheetIndex`, and `foundStyleSheet`.
