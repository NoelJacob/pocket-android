# Pocket/src/main/java/com/pocket/app/reader/internal/article/ArticleViewModel.kt
## What this is
This is the ViewModel behind Article view: it loads the parsed article HTML, images, and videos from `ArticleRepository`, pushes them into the WebView as JavaScript commands, manages highlights, reading position, toolbar state, and display-setting changes. It exposes persistent `uiState` (loading/error/content) and one-shot `events` the fragment executes. Nearly every pixel of the article screen is driven from here.
## How it fits
`ArticleFragment` calls `onInitialized(url)` then `onInitialPageLoaded(theme, density)` once the local HTML shell finishes; the ViewModel emits `ExecuteJavascript(JavascriptFunctions.load(...))` followed by `loadCallback(html)`, streams images/videos in, restores scroll via `ScrollToSavedPosition`, and saves position plus reading time in `onPaused` through `ItemRepository` and a `StopWatch`. Toolbar taps flow through the inner `Toolbar` class (a `ReaderToolbarDelegate`) back out as events like `GoToOriginalWebView` or `ShowTextSettingsBottomSheet`.
## Key pieces
- `loadArticleHtml(forceRefresh)` — sets Loading state, fetches HTML (refresh bypasses cache on toolbar refresh), emits it as JS, then delays ~200ms before showing content so the WebView swap does not flash white. Failures land in `ScreenState.Error` with retry via `onRetryClicked`.
- `onArticleHtmlLoadedIntoWebView` — the post-load pipeline: small delay for a WebView race, `applyHighlights`, restore saved scroll, then stream videos and collect the images flow (each image emits `loadImage` + `requestContentHeight`). `images` accumulates so taps can open `ImageViewerActivity`.
- `onActionModeHighlightClicked / onHighlightPatchRequested` — gates highlight creation on Premium `ANNOTATIONS` feature or the free per-article limit, else emits `ShowHighlightsUpsell`; the JS patch round-trips back and is saved via `HighlightRepository`.
- Inner `Toolbar` — builds saved vs unsaved toolbar and overflow states (favorite, tags, highlights, report, view-original) and maps each tap to the right event or repository call.
## Junior notes
- Display-setting callbacks (`onFontChanged`, `onThemeChanged`, ...) each emit a single JS command — the WebView re-renders live with no reload, but only because the fragment forwards every `ExecuteJavascript` event.
- `onCleared` unregisters the `DisplaySettingsManager` listener; forgetting that would leak the ViewModel after the screen is gone.
