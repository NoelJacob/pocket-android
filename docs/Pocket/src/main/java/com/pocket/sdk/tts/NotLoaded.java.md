# Pocket/src/main/java/com/pocket/sdk/tts/NotLoaded.java
## What this is
This is the placeholder playlist Listen holds before a real one loads: an empty, immutable-feeling `Playlist` that answers "nothing here yet" to every query. It lets the rest of Listen run without null-checking the playlist at every call site.
## How it fits
`Listen` initializes its `playlist` field to `NotLoaded` and replaces it with an `UnreadArticlesList` (or single-track list) once `on()` runs. Between app start and first `on()`, navigation calls (`get`, `before`, `after`), size checks, and `load()` all safely hit this object. `load()` invokes its callback immediately since there is nothing to fetch.
## Key pieces
- `get(i) / get() / size() / before() / after() / indexOf()`: all report empty (-1, null, empty list, 0). WHY: callers can render an empty queue or no-op without special-casing startup.
- `load(onLoaded)`: fires the callback synchronously with itself. Distinguishes "loaded but empty" from "never asked"; `isLoaded()` still returns false so `Listen` knows setup hasn't happened.
- `insert()`: logs a loud developer warning (throws outside production) instead of silently accepting tracks. WHY: inserting into a placeholder means startup ordering broke and hiding it would cause a mysteriously empty queue.
- `clear() / remove() / setListener()`: harmless no-ops. There is nothing to clear and nothing ever changes, so no listener is ever notified.
## Junior notes
- This is the Null Object pattern (an object that stands in for "no value" by doing nothing safely). If you add a method to `Playlist`, you must add the empty behavior here too or startup paths will break.
- `isLoaded() == false` is the signal `Listen` waits on before doing real work. Don't flip it to true to "fix" a loading bug; fix the transition to the real playlist.
- The `insert` warning only throws on dev builds. If you see it in logs, look at who calls `play(track)` before `on()` finishes, not at this class.
