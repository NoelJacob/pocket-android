# Pocket/src/main/java/com/pocket/data/models/Collection.kt
## What this is
UI-friendly models for a Pocket Collection: a curated bundle of stories with a title, intro, author list, and per-story cards. `toDomainCollection()` translates the raw sync-engine `thing.Collection` (generated API type) into these plain Kotlin data classes.
## How it fits
Produced by `CollectionRepository.getCollection()` after fetching a collection by URL slug. Consumed by the collection detail screen, which shows the header (title/intro/authors) and a list of `Story` rows the user can open or save.
## Key pieces
- `Collection` — header fields (`title`, `intro`, `authors`) plus `stories`; everything defaults to empty string/list so a half-filled server response still renders.
- `Author` — just a display name; mapped from the API author list with nulls dropped.
- `Story` — one card: `title`, `excerpt`, `publisher`, `url`, `imageUrl`, plus `isSaved` (derived from the linked item's status) and `isCollection` (whether the story itself links to another collection).
- `toDomainCollection()` — the mapping function; WHY it exists: screens never touch generated API types directly, so null-handling and `isSaved` logic live in one place.
## Junior notes
- `ItemStatus.ARCHIVED` and `UNREAD` both mean "saved" here; anything else (e.g. deleted) means not saved.
- `url.toHttpUrl().pathSegments.last()` in the repository means the slug is the last URL path segment; a trailing slash would change it.
