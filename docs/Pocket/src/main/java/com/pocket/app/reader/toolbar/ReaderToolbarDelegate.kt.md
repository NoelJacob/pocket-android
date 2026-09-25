# Pocket/src/main/java/com/pocket/app/reader/toolbar/ReaderToolbarDelegate.kt
## What this is
This is the reusable brains behind the reader toolbar, meant to be mixed into a reader ViewModel. It implements every toolbar tap: save via the Save use case, archive/un-archive, favorite, delete, mark-as-not-viewed, Listen lookup, share-title lookup, tag-screen lookup, and article reporting. It owns the toolbar StateFlow and the one-shot event flow, so host ViewModels only override getToolbarOverflow() to customize the overflow menu.
## How it fits
A reader ViewModel (Article or Original-web) extends this delegate, sets `url`, and passes its toolbarEvents flow plus itself as the interactions/holder into ReaderToolbarView.setupToolbar(). User taps on the bound layout call back into these methods; the resulting ToolbarEvents are rendered by ReaderToolbarView as back navigation, sign-in, Listen playback, share sheet, overflow popup, or tag screen.
## Key pieces
- `url`: lateinit property the host must set before any tap — WHY every method can act without parameters.
- `_toolbarUiState` / `toolbarUiState`: mutable vs exposed-immutable StateFlow pair so only the delegate mutates visibility.
- `onSaveClicked()` / `onArchiveClicked()` / `onReAddClicked()`: save handles the logged-out case (GoToSignIn event); archive/delete/mark-as-not-viewed also emit GoBack since the item left the current list.
- `onOverflowClicked()` + `getToolbarOverflow()`: emits ShowOverflow with the host-provided flags — the intended override point per reader type.
- `onListenClicked()` / `onShareClicked()` / `onAddTagsClicked()`: coroutine (background task) lookups that degrade gracefully — null track or missing item simply emits nothing useful rather than crashing.
- No-op `onTextSettingsClicked()` / `onViewOriginalClicked()` / `onRefreshClicked()` / `onFindInPageClicked()` / `onHighlightsClicked()`: hooks a subclass overrides for reader-specific behavior.
## Junior notes
- coroutineScope is injected by the host (often viewModelScope); all repository/use-case calls run in launched coroutines because they suspend — never call them from the main thread directly.
- Uses tryEmit for synchronous handlers and emit inside coroutines; tryEmit can drop if the buffer is full, so navigation-critical events from coroutines use suspending emit.
