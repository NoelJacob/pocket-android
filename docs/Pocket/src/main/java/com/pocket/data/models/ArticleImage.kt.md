# Pocket/src/main/java/com/pocket/data/models/ArticleImage.kt
## What this is
A small UI-ready model describing one image inside a saved article. It carries the image id, a local `file://` URL for the downloaded copy, the original remote URL, plus caption and credit text.
## How it fits
Produced by `ArticleRepository.getImages()`, which downloads and caches each article image, then emits one `ArticleImage` per image. Consumed by the article/reader screen to render inline images from local files instead of the network.
## Key pieces
- `ArticleImage` — the model itself; `localFileUrl` is what the WebView actually displays, `originalUrl` is the fallback/source reference.
- `imageId` — matches the server's image id so the UI can key or deduplicate images.
- `caption` / `credit` — display text below the image; already HTML-escaped upstream to block XSS (a cross-site-scripting attack where malicious markup in a caption could run code).
## Junior notes
- `localFileUrl` starts with `file://` because the image was already downloaded to disk; don't load it as a remote URL.
- Captions come from publisher HTML, so never render them unescaped.
