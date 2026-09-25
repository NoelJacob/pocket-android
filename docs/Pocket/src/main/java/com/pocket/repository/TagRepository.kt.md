# Pocket/src/main/java/com/pocket/repository/TagRepository.kt
## What this is
Reads the user's tag list and queues tag renames/deletes. Tagging individual items happens through item actions elsewhere; this repo manages the tag vocabulary itself.
## How it fits
Used by tag management UI (tag list, rename dialog, delete confirmation). Reads come from the local `tags` thing (`getTags()` one-shot, `getTagsAsFlow()` live); writes are fire-and-forget `pocket.sync(null, ...)` actions (`tag_rename`, `tag_delete`) that update locally and sync in the background.
## Key pieces
- `getTags()` / `getTagsAsFlow()` — one-shot vs observable reads of the same `tags` query; WHY both: dialogs need a snapshot, the tag list needs live updates.
- `editTags(tagsMap)` — takes old-name → new-name pairs and queues one `tag_rename` action per entry; WHY a map: the UI can batch several renames in one call.
- `deleteTag(tag)` — queues a single `tag_delete` action.
## Junior notes
- `getTags()` is marked `//TODO broken`, so prefer `getTagsAsFlow()` until that one-shot path is fixed.
- Renames and deletes are fire-and-forget with no `.await()`; the UI should update optimistically and not wait for server confirmation.
