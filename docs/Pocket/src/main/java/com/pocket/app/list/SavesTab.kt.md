# Pocket/src/main/java/com/pocket/app/list/SavesTab.kt
## What this is
This is a two-value enum telling list-related screens whether they are operating on the Saves tab or the Archive tab. Archive and Saves share the same list UI but differ in actions (e.g. swipe archives in Saves, un-archives in Archive), so the current tab travels with bottom sheets and events.
## How it fits
`MyListViewModel.savesTab` derives it from `ListManager`'s list status; it is passed to `TagBottomSheetFragment`, `FilterBottomSheetFragment`, `ItemOverflowBottomSheetFragment`, and `BulkEditOverflowBottomSheetFragment` so each sheet labels and acts correctly. The `value` string (`"saves"`/`"archive"`) feeds analytics contexts.
## Key pieces
- `SAVES("saves")` / `ARCHIVE("archive")` — the only two cases; exhaustive `when` statements over this enum will force you to handle both.
## Junior notes
- The `ponytail:` comment notes this is a local replacement for a deleted analytics enum — keep the string values stable; analytics dashboards key on them.

