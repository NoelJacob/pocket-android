# Pocket/src/main/java/com/pocket/app/reader/Reader.kt
## What this is
This is the shared contract for the reader shell: small interfaces plus the `NavigationEvent` types that describe moving between Article, Collection, and Original Web screens. It also carries the previous/next queue preference. It holds no logic — it is the vocabulary that `ReaderFragment`, `ReaderViewModel`, and the child article/collection/web fragments all speak.
## How it fits
`ReaderViewModel` implements `Initializer`, `PreviousNextInteractions`, and `NavigationInteractions`, and emits `NavigationEvent` subtypes that `ReaderFragment` collects and hands to the current child via `NavigationEventHandler.handleNavigationEvent`. `ArticleFragment` implements that handler to navigate its nested NavController. The `isPreviousAndNextOn` preference gates whether the prev/next bar can appear at all.
## Key pieces
- `NavigationEvent` (`GoToArticle` / `GoToCollection` / `GoToOriginalWeb`, each with `url` + `addToBackstack`) — the single routing message; `addToBackstack` decides between pushing a new screen vs replacing the current one.
- `Initializer.onInitialized(url, initialQueueType, queueStartingIndex)` — the entry call `ReaderFragment` makes once the navigation component is ready, so the ViewModel can build the right queue manager.
- `PreviousNextInteractions` and `NavigationEventHandler` — the two directions of traffic: toolbar prev/next taps flow down into the ViewModel, navigation events flow out to the visible child.
## Junior notes
- Hilt DI provides this `@Singleton` (constructor params filled automatically); the `previousAndNext` preference is per-user, so it follows the logged-in account, not the device.
