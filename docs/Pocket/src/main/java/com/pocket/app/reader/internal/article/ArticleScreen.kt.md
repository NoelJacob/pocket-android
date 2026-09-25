# Pocket/src/main/java/com/pocket/app/reader/internal/article/ArticleScreen.kt
## What this is
This is the contract file for the Article screen: interaction interfaces (what the fragment and toolbars can ask of the ViewModels) plus the `Event` sealed class (the one-shot messages ViewModels send back to `ArticleFragment`). Like `Reader.kt` one level up, it carries no logic — it keeps the fragment, `ArticleViewModel`, `FindTextViewModel`, and recommendations ViewModel speaking the same language.
## How it fits
ViewModels implement the `Initializer`, `WebViewCallbacks`, `FindTextToolbarInteractions`, and toolbar interaction interfaces as user actions and JS callbacks arrive; they emit `Event`s (`ExecuteJavascript`, `OpenImage`, `ShowShare`, `ScrollToSavedPosition`, `OpenNewUrl`, ...) that `ArticleFragment.handleEvent` executes against the WebView, navigators, and bottom sheets.
## Key pieces
- `Event` — the full menu of fragment actions, from `GoBack`/`GoToOriginalWebView`/`GoToSignIn` through `ExecuteJavascript(command)` (the generic JS pipe) to `OpenImage(articleImages, startingId)` and toast/upsell signals. Adding a new article UI affordance usually starts with a new event here.
- `WebViewCallbacks` — the JS-to-native surface (`onInitialPageLoaded`, `onHighlightPatchRequested`, `onInternalLinkClicked`, `onArticleLinkOpened`); implemented by `ArticleViewModel`, invoked from `ArticleFragment.ArticleJsInterface`/`ArticleViewClient`.
- `FindTextToolbarInteractions` / `HighlightOverlayCallbacks` / `ErrorInteractions` — narrow slices so the find toolbar, highlight sheet, and error view each depend only on the callbacks they need.
## Junior notes
- `Event.OpenNewUrl` carries its own `QueueManager` so recommendations can open into a fresh queue while in-article links pass null and stay in the current one.
