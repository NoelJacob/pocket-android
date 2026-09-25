# Pocket/src/test/java/com/pocket/app/MainViewModelTest.kt
## What this is
Unit tests for `MainViewModel`, the app entry-point handler for deep links (URLs that open the app to specific content). It proves reader links emit the right analytics event, syndicated articles and collections open directly in the reader, share links (Pocket share URLs) are resolved first, and short links are un-shortened before opening.
## How it fits
Guards production `com.pocket.app.MainViewModel`, which sits above the reader and list screens at app launch. Uses `FakeItemRepository` (preloaded items by URL/slug) instead of the real network-backed repository, real `Prefs(MemoryPrefStore(), ...)` for preferences, and mocked `ContentOpenTracker` / `UserManager`.
## Key pieces
- `setup()` — builds the ViewModel with fakes; WHY: deterministic link resolution without network.
- Reader-deeplink tests — verify `ReaderEvents` analytics for normal vs short links; WHY: analytics contract for entry points.
- Syndicated/collection tests — assert direct reader open via Turbine `turbineScope` flow assertions; WHY: no resolution round-trip needed for those types.
- Share-link / short-link tests — assert `mapItemToUrl` / `mapItemToShareSlug` resolution happens before reader open; WHY: those URLs must be translated to canonical item URLs first.
## Junior notes
- `FakeItemRepository.mapItemToUrl` and `mapItemToShareSlug` stage the resolution mapping; without them `getItem` returns null and the open path is not exercised.
- Turbine (`turbineScope`, `filterNot`) collects one-shot navigation flows; tests fail if extra unexpected events arrive.
