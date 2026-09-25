# Pocket/src/main/java/com/pocket/sdk/offline/downloader/processor/AssetHandler.java

## What this is
The link-rewriter used while streaming markup: each URL the matchers find is converted to an `Asset` (remote → local mapping) and the original text is replaced with the relative path from the page to that local file. It is a `StreamingMarkupProcessor.LiteralHandler` — a callback that receives each captured URL and returns its replacement text.

## How it fits
`WebDownloader` creates one per page via `forWebPage(url, ...)` and one per stylesheet via `forPageAsset(baseAsset, ...)` (so CSS-relative URLs resolve against the CSS file, not the page). During `StreamingMarkupProcessor.processHtml()`, every matcher hit calls `capture(literal, type)`; the rewritten markup is what gets saved as `web.html`, and discovered assets are reported out through `AssetsOut` for `AssetDownloader` to fetch.

## Key pieces
- `forWebPage(...)` / `forPageAsset(...)` factories — correct base URL + owner for top-level vs nested assets. WHY: relative links (`../img.png`, `/style.css`) only resolve correctly against their containing file.
- `capture(literal, type)` — URL → `Asset.create(...)` → relative-path string. WHY: the single translation point from web address to offline location.
- `relativePath(literal, baseAsset, root)` / `truncatedPath(asset, root)` — path math making links relative to the item folder. WHY: pages must load from any storage root, so absolute paths would break on SD-card moves.
- `AssetsOut` interface — emits each discovered asset upstream. WHY: separates rewriting (this class) from fetching (`AssetDownloader`).

## Junior notes
- Offsets matter: `truncatedPath` assumes the asset is under the given root (`substring(root.length()+1)`). Never pass a root the asset was not built from or you get garbage paths.
- Parent-asset ownership (`AssetUser.forParentAsset`) is created per nested asset; it is what keeps a shared image alive while any referencing CSS needs it.
- `MalformedURLException` from a bad literal should skip that one link, not abort the page — match the surrounding code's per-asset tolerance.
