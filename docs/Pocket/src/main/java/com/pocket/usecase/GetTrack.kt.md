# Pocket/src/main/java/com/pocket/usecase/GetTrack.kt
## What this is
Builds the audio "track" (text-to-speech playback metadata) for one saved URL. It is an invocable use case (`operator fun invoke`, so callers write `getTrack(url)` like a function call).
## How it fits
Called by the listen/ audio player flow. It delegates to `ItemRepository.getItemOrThrow(url)` for the raw sync-engine `Item`, then `toTrack()` (from the TTS module) converts it to a playable track. `ItemRepository` is provided via Hilt DI (constructor parameters supplied automatically).
## Key pieces
- `invoke(url)` — the whole use case in one line: fetch-or-throw, then convert; WHY `getItemOrThrow`: TTS needs full item data, and a missing item is an error, not an empty track.
## Junior notes
- This throws (`NoSuchElementException`/NPE from the repository) when the URL isn't cached; callers must catch or pre-check rather than expecting null.
- It takes the raw `Item`, not a `DomainItem`, because `toTrack()` needs sync-engine fields the domain model strips out.
