# Pocket/src/main/java/com/pocket/app/reader/internal/originalweb/overlay/bottomsheet/OriginalWebBottomSheetViewModel.kt
## What this is
This is the ViewModel behind the bottom-sheet action menu shown over the original-website (web view) reader. It holds the current button states (save/archive/re-add, favorite, viewed, listen) for one URL and performs the tapped action through ItemRepository, the Save use case, or GetTrack. It exposes a StateFlow of UiState for the sheet to render and a SharedFlow of one-shot events (toasts, navigation) for the sheet to consume.
## How it fits
Created by Hilt DI (constructor params provided automatically) when the OriginalWebBottomSheet opens; the sheet calls onInitialized(url) and forwards button taps to the on*Clicked methods. It reads item state via ItemRepository.getDomainItem(url) and writes via archive/unArchive/favorite/delete/markAsViewed, delegating new saves to the Save use case. Downstream it emits OriginalWebBottomSheet.Event values (ShowSavedToast, GoBack, OpenListen, ShowShare, OpenTagScreen, SwitchToArticleView, GoToSignIn) that the sheet fragment turns into toasts, the share dialog, the tag screen, or the Listen player.
## Key pieces
- `onInitialized(url)` / `loadItem()` / `updateItemState(item)`: entry point that stores the URL and maps a DomainItem onto UiState (archived→ReAdd, saved→Archive, else Save; article type enables listen and switch-to-article-view).
- `onMainActionClicked()`: WHY the sheet has one adaptive primary button — Save runs the Save use case (and reloads state on success), Archive archives and closes the sheet, ReAdd un-archives in place.
- `onListenClicked()` / `onShareClicked()` / `onAddTagsClicked()`: async lookups (GetTrack for audio, displayTitle for sharing, full Item for tagging) that emit OpenListen / ShowShare / OpenTagScreen events.
- `onFavoriteClicked()`, `onMarkAsViewedClicked()`, `onDeleteClicked()`, `onSwitchToArticleViewClicked()`: direct repository toggles plus matching UiState updates; delete resets to Save state and emits GoBack.
- `UiState`, `MainActionState`, `FavoriteState`, `ViewedState`, `ListenState`: sealed UI-state types carrying string/drawable/color ids so the sheet renders without branching on raw item flags.
## Junior notes
- StateFlow is an observable state stream the UI collects for rendering; SharedFlow (events) is for one-shot happenings like toasts — collected with a lifecycle-aware helper so rotations do not replay them.
- viewModelScope.launch runs background tasks (coroutines) tied to the ViewModel's lifetime; repository calls that throw (unknown URL) are caught and fall back to Save/Disabled states.
