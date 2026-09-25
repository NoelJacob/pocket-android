# Pocket/src/main/java/com/pocket/app/reader/internal/article/ArticleFragment.kt
## What this is
This is the Article view screen: it loads a local HTML shell into a `WebView`, injects the parsed article HTML plus images, videos, highlights, and display settings through JavaScript, and hosts the toolbar, find-in-page bar, end-of-article recommendations, and highlight/share sheets. It is the screen users read cleaned-up articles in, including scrolling, text selection, link long-press, and premium upsells.
## How it fits
It lives as a child of `ReaderFragment`'s nested graph (entered via `enterArticle`/`switchToArticle` directions) and is driven by three ViewModels: `ArticleViewModel` (content + toolbar + highlights), `EndOfArticleRecommendationsViewModel`, and `FindTextViewModel`. It translates their `ArticleScreen.Event`s into real UI (executing JS, navigating, showing bottom sheets via `childFragmentManager`, opening `ImageViewerActivity`), and feeds WebView/JS callbacks (`ArticleJsInterface`, `ArticleViewClient`) back into the ViewModels.
## Key pieces
- `setupWebView` + `ArticleJsInterface` — enables JavaScript, registers the `PocketAndroidArticleInterface` bridge (`onReady`, `onRequestedHighlightPatch`, `scrollToPosition`, `setViewType`), and sets long-press handling for links (save vs copy). This bridge is the only path article HTML talks to native code.
- `handleEvent` — the big router: `ExecuteJavascript` runs strings in the WebView, `OpenNewUrl` delegates to `readerFragment.openUrl` (so links get destination resolution), `GoToOriginalWebView`/`switchTo*` navigate the nested graph, share/highlight/text-settings open their sheets.
- `handleNavigationEvent` — receives `Reader.NavigationEvent`s from the parent and either pushes (`enter*`) or replaces (`switchTo*`) based on `addToBackstack`, which is what makes tapping links stack while prev/next replaces.
## Junior notes
- `collectWhenCreated` vs `collectWhenResumed`: event collection here survives paused states so JS-load events are not lost while a bottom sheet is up — do not "fix" it to resumed without testing highlights and images.
- `allowScrollChange` is flipped off during programmatic scrolls (e.g. `scrollToPosition` from JS) so the auto-fullscreen scroll listener does not fight the jump and hide the toolbar.
