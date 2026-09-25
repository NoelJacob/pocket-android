# Pocket/src/main/java/com/pocket/repository/ArticleRepository.kt
## What this is
Serves rendered article content for the reader: the article HTML from the offline cache (downloading it first if missing), the article's images as a stream, video metadata, and a "user opened this article" report call.
## How it fits
Called by the article/reader screen and its ViewModel. It sits above the sync engine (`Pocket`), the offline download cache (`Assets`, `OfflineDownloading`), and the image pipeline (`Image`); downstream it feeds HTML/JS into the WebView. A "sync" here means reconciling local cached things with the server; this repo mostly reads the local cache and triggers downloads.
## Key pieces
- `getArticleHtml(url, forceRefresh)` — looks up the local `Item`, resolves its cached HTML file, and if absent/stale kicks off `offlineDownloading.download()` and waits on a `Mutex` (a mutual-exclusion lock used here as a one-shot gate) before reading the file; WHY `suspend` (a coroutine, i.e. background-task, function that can pause without blocking): downloading takes time.
- `getImages(url, imageWidth)` — a `callbackFlow` (a `Flow`, i.e. observable stream, bridged from callbacks) emitting one `ArticleImage` per cached image at the requested width; captions/credits are HTML-escaped against XSS.
- `getVideoJson(url)` — converts each video to a Jackson `ObjectNode` (a generic JSON object) for the WebView player.
- `reportArticle(url)` — fire-and-forget `reportArticleView` action so the server (and recommendations) know the article was viewed.
## Junior notes
- `getArticleHtml` blocks on a `Mutex` that the download callback unlocks; if the callback never fires (download error), this hangs. Treat download failures as a real hang risk.
- `getImages` uses `trySendBlocking` because the image cache callback is not a coroutine; the `awaitClose` can't cancel in-flight image fetches.
