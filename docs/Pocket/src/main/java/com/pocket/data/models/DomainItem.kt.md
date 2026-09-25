# Pocket/src/main/java/com/pocket/data/models/DomainItem.kt
## What this is
The central product model for a single saved thing: a URL the user saved, with its title, type, saved/archived/favorite flags, highlights, reading position, and media info. `id` is nullable because a freshly saved URL has no server id until the next sync.
## How it fits
Produced by `Item.toDomainItem()` from the sync-engine `Item`. `ItemRepository` (via `getDomainItem()` / `getDomainItemFlow()`) hands these to the reader, Saves list, and detail screens; `HighlightRepository.getHighlightsFlow()` also derives highlights through it.
## Key pieces
- `DomainItem` — the whole card/reader state: `idUrl` (the canonical saved URL, never null), `displayTitle`, `isSaved`/`isArchived`/`isFavorited`, `highlights`, `positions`, `isViewed`, `resolvedUrl`, `wordCount`, `videos`.
- `ItemType` (`VIDEO`, `ARTICLE`, `INDEX`, `OTHER`) — coarse content kind; WHY: the UI picks reader vs video vs fallback layout from this, not from raw flags.
- `itemType()` — derives `ItemType` from `has_video` / `is_article` / `is_index`; video wins over article.
- `toDomainItem()` — null-tolerant mapping (`orEmpty()`, `?: false`, `?: emptyList()`); WHY: server fields are optional, UI fields are not.
- `DomainPosition` / `PositionType` — where the user left off per view (article vs web vs video), including scroll position and time spent; `syncPosition` is the raw sync-engine position kept for writes.
- `DomainVideo` / `toDomainVideo()` — just the video length, used for watch-time display.
## Junior notes
- `idUrl` is the stable key for local lookups; `id` is the server id and may be null for unsynced saves.
- `isSaved` is true for both `UNREAD` and `ARCHIVED`; check `isArchived` when you need to tell My List apart from Archive.
