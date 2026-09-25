# Pocket/src/main/java/com/pocket/repository/TopicsRepository.kt
## What this is
Serves Discover topics: the browsable topic list and each topic's curated + algorithmic feed. Topic content is cached so the Discover screen works offline after first load.
## How it fits
Used by the Discover/topic screens. The topic list is pinned once in a persistent `Holder` (`"topics"`, a disk-cached bucket) during `init`; per-topic feeds use a session-scoped holder (`"topicSession"`). List reads observe the local cache as a Flow; feed reads fetch fresh via `refreshTopic()`. Sync-wise this is read-only: `syncRemote`/`get` pull server data in, nothing here writes.
## Key pieces
- `topics` — the single `discoverTopicList` query remembered at init; WHY a field: every list method reuses the same cached query object.
- `refreshTopics()` / `getTopicsAsFlow()` / `getTopicsLocal()` / `hasCachedTopics()` — remote refresh, live Flow, one-shot local read, and the "can we show Discover offline?" check, respectively.
- `refreshTopic(topicId)` / `getTopicAsFlow(topicId)` — per-topic feed fetch (5 curated + 20 algorithmic cards) and its live observer; refresh pins the feed in the session holder then `get()`s it.
- `CURATED_COUNT` (5) / `ALGORITHMIC_COUNT` (20) — editorial picks vs personalized picks per topic feed.
## Junior notes
- `remember()` runs inside `pocket.setup { }`, i.e. once the sync engine is ready; calling list methods before setup completes can miss the cache pin.
- Topic feeds are session-scoped: rotating through many topics in one session accumulates cached feeds under one holder, while the topic list itself persists across restarts.
