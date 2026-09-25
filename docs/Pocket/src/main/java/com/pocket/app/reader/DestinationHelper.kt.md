# Pocket/src/main/java/com/pocket/app/reader/DestinationHelper.kt
## What this is
This decides which reader screen a URL should open: cleaned-up Article view, Collection view, or the raw Original Web page. It looks up the saved item, normalizes the URL scheme, and applies a priority chain (forced web, video, collections, syndicated articles, user preference, saved articles). Every link opened in the reader funnels through this one decision.
## How it fits
`ReaderViewModel.openUrl` calls `getDestination(url, forceOpenInWebView)` inside a coroutine (a background task that can suspend without blocking the UI thread) and translates the returned `Destination` into a `Reader.NavigationEvent` (`GoToArticle` / `GoToCollection` / `GoToOriginalWeb`). `ReaderFragment` then forwards that event to whichever child fragment is visible. A null item lookup (repository throws) is treated as "unknown" and falls through to web.
## Key pieces
- `getDestination` — the priority chain itself; order matters (e.g. `forceOpenInWebView` and `VIDEO` beat everything, the kill switch demotes syndicated articles to web). Returns the single `Destination` enum.
- `isCollection / isSyndicatedArticle / isHostedByPocket` — URL-shape matchers for `getpocket.com/.../collections/...` and `.../explore/item/...` paths. These are what route Pocket-curated content to their native screens.
- `articleViewKillSwitchFlag` and `appPrefs.ALWAYS_OPEN_ORIGINAL` — the two escape hatches: a server flag that disables Article view globally if the parser breaks, and a user setting that forces original web when online.
## Junior notes
- `StateFlow`/`SharedFlow` are observable state streams the UI collects; here the destination itself is just a suspend return value, not a flow — the event flow happens one layer up in the ViewModel.
- URLs without a scheme get `https://` prepended before parsing, so bare domains from shares or intents do not crash `toHttpUrl()`.
