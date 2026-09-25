# Pocket/src/test/java/com/pocket/fakes/FakeItem.kt
## What this is
A `fakeItem()` factory that builds a fully-populated `Item` (the generated API model for a saved story) with sensible defaults: id, URL, unread status, title, domain, image, and flags. One-line fixture for any test needing an item without hand-building every field.
## How it fits
Used by `MyListViewModelTest`, `ItemOverflowBottomSheetViewModelTest`, and other list/overflow suites. The fake exists because real `Item`s come from the server with dozens of fields; tests only care about a few, so defaults fill the rest.
## Key pieces
- `fakeItem(itemId, idUrl, itemStatus, title, domain, topImageUrl, favorite, viewed)` — all-optional params; WHY: callers override only what the scenario needs.
- `Item.Builder` plus `Item.IdBuilder().id_url(...)` chain — constructs the generated immutable model; WHY: mirrors how production items are shaped.
## Junior notes
- `id_url` (identity URL) differs from `given_url` (original URL); most lookups key on `id_url`, so override `idUrl` for distinct items.
- `ItemStatus.UNREAD` default means archived/favorited scenarios must pass `itemStatus` explicitly.
