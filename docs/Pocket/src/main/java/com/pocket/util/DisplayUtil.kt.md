# Pocket/src/main/java/com/pocket/util/DisplayUtil.kt

## What this is
A small formatter that turns raw article data into human-readable list strings. It solves two display problems: shortening a saved URL to its host ("www.example.com/article" becomes "example.com") and joining an author list into "Alice, Bob". For example, the Listen (text-to-speech) screens show `displayHost(track.displayUrl)` under each episode title.

## How it fits
It is a stateless `object` (Kotlin singleton) called directly from UI code wherever a host or byline is shown. Known callers are `ListenItemAdapter`, `ListenPlayerView`, `ListenView`, and `ListenMediaService` (which uses the host as the notification album label). It delegates host parsing to `DomainUtils.getHost` and string work to Apache `StringUtils`.

## Key pieces
- `displayHost(url)`: extracts the host via `DomainUtils.getHost` and strips a leading "www.". WHY it exists: list rows and notifications need a short, recognizable source label, not a full URL.
- `displayAuthors(authors)`: returns null for an empty list, the single name for one author, or a comma-joined string otherwise. WHY it exists: one shared rule for bylines so every screen formats them identically.

## Junior notes
- `@JvmStatic` makes these callable as plain static methods from Java files (most callers here are Java); from Kotlin they are just `DisplayUtil.displayHost(...)`.
- `displayAuthors` returns null (not "") when there are no authors, so callers like `ListenPlayerView` branch on emptiness first — always null-check the result.
- This is pure formatting with no Android dependencies, which makes it trivially unit-testable.
