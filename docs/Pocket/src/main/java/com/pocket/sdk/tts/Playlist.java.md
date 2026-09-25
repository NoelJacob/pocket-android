# Pocket/src/main/java/com/pocket/sdk/tts/Playlist.java
## What this is
This is the queue contract for Listen: an ordered list of `Track` articles with loading, lookup, insert/remove, and next/previous navigation. Both the real queue (`UnreadArticlesList`) and the startup placeholder (`NotLoaded`) implement it, so `Listen` never cares which one is installed.
## How it fits
`Listen` holds one `Playlist`, calls `load()` during `on()`, then uses `get/before/after/indexOf` to move through articles as each `ListenPlayer` completion fires. UI reads `get()` for the queue display; `setListener` lets the queue push change events (an article's metadata updated) back to `Listen` for re-render. Single-article playback is just a queue with one entry.
## Key pieces
- `load(callback)`: async setup hook. Already-loaded queues invoke the callback immediately; loading ones fetch (e.g. a sync query for unread saves) and then call back. WHY callback-shaped: loading can hit the network or disk.
- `get(i) / get() / size()`: positional access plus a defensive-copy list getter. The copy matters: callers can't corrupt the live queue by sorting or filtering what they got.
- `indexOf(url/Track) / contains(track) / isLast(track)`: identity helpers. `isLast` (default method) is what end-of-queue logic uses to decide "stop" versus "advance".
- `before(current) / after(current)`: neighbor navigation returning null at the ends. `Listen` treats null-after as end-of-list (auto-play policy decides stop vs loop) and null-before as "already at the top".
- `insert(index, track) / remove(track) / clear()`: queue editing for play-single-item and swipe-to-remove. Indexes past the end append rather than throw, matching user intent.
- `Listener / OnLoad`: the two callbacks — ongoing change pings versus one-shot load completion. A queue that never changes (like `NotLoaded`) can ignore the listener entirely.
## Junior notes
- `Track` equality is by article identity (`idUrl`), not object instance. Two `Track` objects for the same article are interchangeable in `indexOf`/`remove`.
- `get()` returns a copy on every call. Don't call it in a tight loop; hold the reference for the duration of your pass.
- `load()` may call back synchronously (already loaded) or async. Write callers to handle both; assuming async causes "works on second tap" bugs.
