# Pocket/src/test/java/com/pocket/testutils/SharedFlowTracker.kt
## What this is
Tiny helper that records every emission of a `SharedFlow` (a hot observable stream used for one-shot events like navigation) into `sharedFlowUpdates`, exposing `firstValue` / `lastValue`. Lets tests assert on navigation and one-shot UI events without writing collectors.
## How it fits
Attached to ViewModel event flows in `BulkEditOverflowBottomSheetViewModelTest`, `TagBottomSheetViewModelTest`, and `MyListViewModelTest` (e.g. `SharedFlowTracker(subject.navigationEvents)`). Exists because `SharedFlow` has no replay/value to read — something must collect to observe.
## Key pieces
- `SharedFlowTracker(sharedFlow)` plus `sharedFlowUpdates` — subscribes on init, appends each emission; WHY: observable history of events.
- `firstValue` / `lastValue` — convenience accessors; WHY: most tests assert the single or latest navigation.
## Junior notes
- Collection launches on `Dispatchers.Main`, so the test must provide it (`BaseCoroutineTest` or `MainDispatcherRule`) or events are missed.
- `firstValue`/`lastValue` throw on empty lists — if they throw, the event was never emitted (or the tracker was created after emission).
