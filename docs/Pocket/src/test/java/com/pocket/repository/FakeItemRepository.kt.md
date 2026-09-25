# Pocket/src/test/java/com/pocket/repository/FakeItemRepository.kt
## What this is
In-memory fake of `ItemRepository` (the saved-items data source). It stores items in lists/maps keyed by URL and share slug, supporting lookup, delete, and save, with helpers staging resolution mappings. Unneeded mutating methods are `TODO` stubs.
## How it fits
Injected into `MainViewModelTest` (link resolution) and `AddUrlBottomSheetViewModelTest` (via the real `Save` use case). The fake exists because the real repository needs network, database, and sync; tests need only lookup/save/delete semantics.
## Key pieces
- `items / itemsByUrl / itemsByShareSlug` — in-memory stores; WHY: deterministic fixtures without persistence.
- `getItem / getItemOrThrow / getDomainItem / getItemByShareSlug` — lookup paths; WHY: mirror the resolution flows production exercises (direct, slug, share-link).
- `save(url)` — appends a minimal item; `delete(...)` — removes by URL; WHY: enough mutation for add/remove scenarios.
- `mapItemToUrl(resolvedUrl, givenUrl)` / `mapItemToShareSlug(url, slug)` — stage alias mappings; WHY: simulate short-link and share-link resolution.
- `TODO` stubs (`toggleFavorite`, `archive`, `markAsViewed`, etc.) — intentionally unimplemented; WHY: keeps the fake small; tests needing those use MockK mocks instead.
## Junior notes
- Calling an unimplemented method throws `NotImplementedError` — if your new test needs it, implement just that method rather than switching fakes.
- `getDomainItemFlow` is also unimplemented, so flow-collecting tests cannot use this fake as-is.
