# Pocket/src/main/java/com/pocket/sdk/tts/Track.kt
## What this is
This is one playable row in the Listen queue: a flattened, UI-ready view of a saved Pocket item (title, URLs, authors, thumbnail, word count, saved position) plus the backing sync `Item` for live updates. A Kotlin `data class` here is just a value holder with generated equality, so two tracks for the same article compare equal.
## How it fits
`UnreadArticlesList` builds tracks from synced `Item`s via `toTrack()` when the queue loads; single-article playback wraps one item the same way. `Listen` carries the current track in `ListenState.current` and the full queue in `ListenState.list`; the player UI, notification, and media session read display fields from it, while `TTSPlayer`/`GetItemAudioPlayer` use `itemId`/`idUrl` to load content and `positions` to resume where the user left off.
## Key pieces
- `Track` fields: `syncItem` (live sync object that updates when the item changes), `itemId`/`idUrl` (server identity vs canonical URL identity), display fields (`displayTitle`, `displayThumbnailUrl`, `displayUrl`, `openUrl`), `authors`, `timeAdded`, `positions` (saved scroll/listen offsets), `listenDurationEstimate`, `itemType`, `wordCount`. WHY both IDs: some operations key on server ID, others (sessions, deep links) on URL.
- `toTrack()`: the `Item`-to-`Track` mapper with null-safe defaults (empty strings, empty lists, 0 words). Centralizes the "what does Listen show for an item" decision so queue and single-play agree.
- `isVideo / isArticle`: convenience flags off `itemType`. Listen primarily plays articles; video items take a different path and `isListenable()` in `Listen` consults word counts.
- `articlePosition`: first `ARTICLE`-type position from the positions list — the resume point `TTSPlayer` seeks to and `saveProgress()` writes back.
## Junior notes
- `syncItem` is a live sync-engine object: its fields can update underneath you (title corrected, archived). The queue subscribes to those changes and swaps in a fresh `Track`; never cache display strings outside state and expect them to stay current.
- `idUrl` is non-null by force (`!!`). An item without one can't be a track at all — that crash is intentional, fail fast rather than queueing an unplayable row.
- Equality includes all fields, so a metadata refresh produces an unequal-but-same-article track. Queue `indexOf` still works because it compares the same way, but don't use `===` (reference equality) for track identity.
