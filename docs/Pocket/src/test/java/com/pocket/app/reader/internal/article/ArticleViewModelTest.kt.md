# Pocket/src/test/java/com/pocket/app/reader/internal/article/ArticleViewModelTest.kt
## What this is
Single-behavior test for `ArticleViewModel`, the article reader screen. It proves opening an in-article link sends an `articleLinkContentOpen` event through `ContentOpenTracker`.
## How it fits
Guards production `ArticleViewModel`, hosted inside `ReaderViewModel` and backed by `ArticleRepository`, `ItemRepository`, `HighlightRepository`, `DisplaySettingsManager`, `PremiumFonts`, and the `Save` use case — all relaxed mocks here.
## Key pieces
- `setup()` — constructs the ViewModel with nine mocked dependencies; WHY: isolates the link-open path.
- `WHEN an article link is opened THEN an analytics event is sent` — calls `onArticleLinkOpened("url")`, verifies `contentOpenTracker.track(articleLinkContentOpen("url"))` exactly once; WHY: outbound-link reporting contract.
## Junior notes
- `Tracker` vs `ContentOpenTracker`: outbound content opens go to the latter; do not assert on the generic tracker here.
- Everything else the ViewModel does (highlights, display settings, fonts) is untested in this file.
