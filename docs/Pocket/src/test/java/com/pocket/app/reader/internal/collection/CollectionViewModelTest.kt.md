# Pocket/src/test/java/com/pocket/app/reader/internal/collection/CollectionViewModelTest.kt
## What this is
Tests for `CollectionViewModel`, the Pocket collection reader (a curated set of stories). It proves tapping a story card tracks a `contentOpen` event, and saving a story tracks `recommendationSaveClicked` after the collection loads.
## How it fits
Guards production `CollectionViewModel`, hosted in the reader and backed by `CollectionRepository`, `ItemRepository`, `ArticleRepository`, and the `Save` use case. Repositories are relaxed mocks; the save test stubs `getCollection` to return a one-story `Collection`.
## Key pieces
- `setup()` — builds the ViewModel from mocks; WHY: isolates tap/save reporting.
- Card-click test — `onCardClicked("url")` verifies `contentOpenTracker.track(CollectionEvents.contentOpen("url"))`; WHY: story-open reporting.
- Save test — stubs `collectionRepository.getCollection`, calls `onInitialized` then `onSaveClicked`, verifies `tracker.track(recommendationSaveClicked("url"))`; WHY: save must resolve against a loaded collection first.
## Junior notes
- `coEvery` (not `every`) stubs the suspend `getCollection`; using the wrong one silently fails to stub.
- `onInitialized` must precede `onSaveClicked` — the ViewModel needs the loaded stories to attribute the save.
