# Pocket/src/test/java/com/pocket/app/home/saves/RecentSavesViewModelTest.kt
## What this is
Tests for `RecentSavesViewModel`, the Home "recent saves" row. It proves viewing a save sends a `recentSavesImpression` event and tapping one sends a `recentSavesCardContentOpen` event with URL and position.
## How it fits
Guards production `RecentSavesViewModel`, backed by `SavesRepository` and `ItemRepository` and rendered on the Home screen. Verifies the analytics half; repository data flow is mocked out.
## Key pieces
- `setup()` — constructs the ViewModel from relaxed mocks; WHY: isolates analytics behavior.
- `WHEN an item is viewed THEN an analytics event is sent` — calls `onSaveViewed(1, "url")`, verifies `tracker.track(recentSavesImpression(...))`; WHY: impression reporting for the row.
- `WHEN an item is clicked THEN an analytics event is sent` — builds a minimal `Item` with a URL, calls `onItemClicked`, verifies `contentOpenTracker.track(recentSavesCardContentOpen(...))`; WHY: content-open reporting for taps.
## Junior notes
- `Item.Builder().given_url(...)` is the minimal item needed; position is passed through exactly per the event contract.
- Again two trackers: impressions go to `Tracker`, opens go to `ContentOpenTracker`.
