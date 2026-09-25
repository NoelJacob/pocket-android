# Pocket/src/test/java/com/pocket/app/list/list/ListManagerTest.kt
## What this is
Tests for `ListManager`, the plain state holder for list sort/filter/search (tag, sort key, filter set, status, search text). It proves setters update `sortFilterState`: setting a tag, updating sort, toggling/adding/clearing filters, switching saves-vs-archive status, and editing search text.
## How it fits
Guards production `com.pocket.app.list.list.ListManager`, consumed by `MyListViewModel`, `FilterBottomSheetViewModel`, and `TagBottomSheetViewModel`. `Pocket`, `Preferences`, and `PocketCache` are relaxed mocks; assertions read `subject.sortFilterState.value` directly.
## Key pieces
- `setup()` — builds `ListManager` from mocks; WHY: state logic has no real dependencies.
- `set tag / updating sort` — single-value setters; WHY: baseline state transitions.
- `filter toggle / add filter / clear filters` — multi-select filter set operations; WHY: pins toggle-vs-add-vs-clear semantics.
- `status filter / search text` — saves-vs-archive switching and query editing; WHY: these drive which list query runs.
## Junior notes
- `ListManager` holds state in a `StateFlow` (an observable state stream); tests read `.value` synchronously because `BaseCoroutineTest` swaps Main.
- No analytics or repository here — this is pure state logic, so tests assert state, not interactions.
