# Pocket/src/main/java/com/pocket/app/reader/ReaderViewModel.kt
## What this is
This is the ViewModel (a lifecycle-aware holder of screen state that survives rotation) behind the reader shell. It resolves each URL to a destination via `DestinationHelper`, emits one-shot navigation events, marks items viewed, and maintains a stack of queue managers that power the previous/next bar. It is where "which screen" and "what are prev/next" are decided.
## How it fits
`ReaderFragment` calls `onInitialized` once (building a `SavesListQueueManager` or `EmptyQueueManager` from the entry args) and `openUrl` for every subsequent link; the ViewModel emits `Reader.NavigationEvent`s on a `SharedFlow` (a one-shot event stream, unlike state) that the fragment forwards to the visible child. Prev/next taps pop URLs out of the top `QueueManager`; `onBackstackPopped` pops the stack when the user goes back out of a nested queue such as a collection.
## Key pieces
- `openUrl(url, queueManager, forceOpenInWebView)` — launches a coroutine, resolves the destination, emits the matching event, marks the item viewed, and pushes a non-null queue manager (which also sets `addToBackstack = true` on the event). The queue push and the viewed-mark happen for every open, so both stay in sync.
- `queueManagerStack` — a stack (not a single manager) because entering a collection from inside a saves queue nests one queue inside another; back unwraps one level and refreshes the bar.
- `UiState(previousAndNextBarVisible, previousVisible, nextVisible)` — derived in `updatePreviousNextState` from the top manager plus the `reader.isPreviousAndNextOn` user setting; the bar only shows when there is somewhere to go and the user allows it.
## Junior notes
- `MutableStateFlow` is persistent UI state the layout observes; `MutableSharedFlow` with `extraBufferCapacity = 1` is fire-and-forget events — using state for navigation would re-navigate on every rotation.
- `hasNext/hasPrevious` are synchronous getters over the stack top used by databinding; the authoritative bar visibility still comes from `uiState`.
