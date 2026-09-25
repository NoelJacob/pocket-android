# Pocket/src/main/java/com/pocket/app/reader/internal/article/ArticleWebView.kt
## What this is
This is a thin `WebView` subclass used for article content whose only job is to replace the default text-selection menu with Pocket's own (Highlight and Share actions). It delegates both `startActionMode` overloads to `ArticleActionModeCallback`. Without it, long-pressing article text would show the stock Android copy/paste bar instead of Pocket actions.
## How it fits
`ArticleFragment`'s layout instantiates it as the article surface; the fragment sets `onHighlightActionModeClicked` (routes to `ArticleViewModel.onActionModeHighlightClicked`) and `onShareActionModeClicked` (routes to `onActionModeShareClicked`). `executeJS` is the fragment's pipe for running `ArticleScreen.Event.ExecuteJavascript` commands via `evaluateJavascript`.
## Key pieces
- `startActionMode(callback)` / `startActionMode(callback, type)` — both wrapped identically so the custom menu appears on old floating-menu and new overlay paths alike; overriding only one would leave one Android version with the stock menu.
- `clipboard` (`@Inject`) — Hilt-injected helper passed into the callback so copy actions use the app's clipboard wrapper rather than raw system calls.
## Junior notes
- `@AndroidEntryPoint` is the Hilt marker that enables field injection into Android framework classes like views — plain `new ArticleWebView()` outside an injected layout would leave `clipboard` uninitialized.
