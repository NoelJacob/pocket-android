# Pocket/src/main/java/com/pocket/sdk/tts/UnreadArticlesList.java
## What this is
This is the default Listen queue: the user's unread articles, up to 50, in their current list sort order, filtered by word count so tiny stubs and giant epics can be excluded. Once loaded, the membership is frozen — archiving or saving mid-listen doesn't reshuffle the queue — but each row's metadata stays live.
## How it fits
`Listen` creates one of these in `initPlaylist()` during `on()` and calls `load()`; the load runs a sync query (a fetch against Pocket's local-synced store, which mirrors server data) for unread article saves with the user's sort, word-count bounds, and a count cap, mapping each result via `toTrack()`. As `ListenPlayer` completions fire, `Listen` walks this queue with `before/after`; the player UI reads `get()` for the up-next list. `clear()` tears it down on `off()`.
## Key pieces
- `load(onLoaded)`: one-shot sync query for unread articles honoring sort and word-count prefs, inserting each hit as a track, then firing the callback on the UI thread. Already-loaded calls back immediately; there is no paging beyond `COUNT = 50`.
- `insert(index, track)`: appends (or splices) plus subscribes to that item's sync changes so title/thumbnail/status refreshes swap the row in place and ping the listener. WHY per-item subscriptions: frozen membership, live metadata.
- `before(current) / after(current)`: neighbor lookup by identity with null at the ends. `Listen` maps null-after to end-of-queue policy (stop or loop) and null-before to "restart the top".
- `clear()`: nulls the list and stops every item subscription on a worker thread. Async because unsubscribing many items touches the sync store and can be slow; the copy-then-clear avoids races with in-flight updates.
- `indexOf(url/Track) / get(i) / get() / size() / remove(track)`: standard queue access. `get()` returns a copy so UI sorting can't corrupt playback order.
## Junior notes
- The 50-item cap and frozen membership are deliberate simplifications (noted in the class javadoc). Auto-paging or live membership changes need product agreement; don't bolt them on casually.
- `minWordCount/maxWordCount` come from prefs and gate `isListenable`. Miscategorized items (wrong word count server-side) silently vanish from Listen — check filters before assuming a missing article is a sync bug.
- Subscriptions are per track and must be stopped in `clear()`. Leaking them keeps sync observers alive and fires `onPlayListChanged` into a dead queue.
