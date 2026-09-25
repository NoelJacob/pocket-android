# Pocket/src/main/java/com/pocket/app/reader/internal/article/find/FindTextViewModel.kt
## What this is
This is the ViewModel for find-in-page (the "search this article" toolbar): it tracks the query, the match count reported back from JavaScript, and which match is currently shown. It emits JavaScript search/scroll commands and exposes a `UiState(countText, visible)` the toolbar binds to. It is a small state machine over the WebView's text search.
## How it fits
`ArticleFragment` binds the find toolbar inputs to it (text changes to `onTextChanged`, arrows to `onNext/onPreviousClicked`, cancel to `onCloseClicked`) and shows the bar on `ArticleScreen.Event.ShowTextFinder` via `onShow`. Search requests go out as `ExecuteJavascript(searchForText/clearSearchText/scrollToSearchText)` events; the match count comes back through `ArticleJsInterface.onTextSearch` into `onTextHighlighted`.
## Key pieces
- `onTextChanged` — blank query clears highlights and resets counters; non-blank emits `searchForText` and waits for the JS count callback. The counting itself lives in page JS, not here.
- `onNext/PreviousClicked` — wraps `currentInstance` around `count` (0 when empty, wrap from last to first) and updates the `current/total` string plus a scroll command. Wrapping is why tapping next past the last match cycles instead of stopping.
- `onCloseClicked` — hides the bar and emits `clearSearchText` so stale highlights do not linger in the article.
## Junior notes
- `countText` ("3/12") is derived state updated by `updateCountString` after every change — if you add a search path, call it or the toolbar label goes stale.
- Back press is intercepted in `ArticleFragment.onBackPressed` to close find UI first, so this ViewModel never handles the system back button itself.
