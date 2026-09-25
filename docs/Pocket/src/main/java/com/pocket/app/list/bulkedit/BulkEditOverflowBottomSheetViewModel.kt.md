# Pocket/src/main/java/com/pocket/app/list/bulkedit/BulkEditOverflowBottomSheetViewModel.kt

## What this is
ViewModel behind the "..." overflow menu in bulk-edit mode, when the user has selected multiple saves. It builds the sheet title ("N selected") and the favorite/unfavorite label, and executes the chosen batch action before closing the sheet.

## How it fits
Created by the bulk-edit overflow bottom sheet via Hilt DI (constructor params provided automatically). It receives the selected `Item` list plus the current `SavesTab` through `onInitialized`, calls `ItemRepository` to apply favorite/unfavorite or mark viewed/not-viewed, and emits a one-shot `navigationEvents` flow (a `SharedFlow`, i.e. an observable event stream that does not hold state) telling the fragment to `Close` or `OpenTagScreen`.

## Key pieces
- `onInitialized(items, savesTab)` — WHY: caches the selection and sets the title plus the smart favorite label (shows "Favorite" if any item is unfavorited, else "Unfavorite").
- `onFavoriteClicked()` — WHY: toggles the whole batch in one call, then closes the sheet.
- `onEditTagsClicked()` — WHY: defers to the tag screen instead of acting here; just emits `OpenTagScreen`.
- `onMarkAsViewedClicked()` / `onMarkAsNotViewedClicked()` — WHY: batch view-state changes, then close.
- `BulkEditOverflowBottomSheetUiState` — WHY: the rendered state (`title`, `favoriteText`); observed as a `StateFlow` (observable state stream the UI collects).
- `BulkEditOverflowNavigationEvent` — WHY: one-shot close/navigate events, separate from persistent UI state so they are not re-consumed on rotation.

## Junior notes
- `StateFlow` holds the current UI state; `SharedFlow` with `extraBufferCapacity = 1` fires single events — collect navigation events with a one-shot collector, not as state.
- `containsUnFavorited()` treats a null `favorite` as "not favorited", so mixed selections always offer "Favorite" first.
