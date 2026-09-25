# Pocket/src/main/java/com/pocket/app/list/bulkedit/BulkEditOverflowBottomSheetFragment.kt
## What this is
This is the overflow ("...") bottom sheet for bulk-edit mode: extra batch actions (notably tagging) for the currently multi-selected saves. The sheet itself is thin — row content and actions live in `BulkEditOverflowBottomSheetViewModel` (outside this chunk); this file wires the selection in and routes the two navigation outcomes.
## How it fits
Shown from `MyListFragment` on `ShowBulkEditOverflowBottomSheet`, built via `newInstance(items, savesTab, onDismiss)` carrying the selected `Item`s, the current `SavesTab` (Saves vs Archive behavior), and a callback to exit edit mode. `viewModel.onInitialized(items, savesTab)` runs on view creation. `Close` invokes `onDismiss` (which calls `viewModel.onBulkEditFinished()`) then dismisses; `OpenTagScreen` opens `ItemsTaggingFragment` with a mutable copy of the selection (untagged-mode `false`, no initial tag) before dismissing the same way.
## Key pieces
- `newInstance(items, savesTab, onDismiss)` — field-based handoff including a lambda callback; simple but, like the other sheets here, lost on process death.
- `setupNavigationEventObserver()` — the two-branch `when` over `BulkEditOverflowNavigationEvent`; both branches call `onDismiss` so edit mode always exits, whether tagging or just closing.
- `setupAnalytics()` — empty placeholder mirroring the other sheets.
## Junior notes
- `mutableListOf<Item>().apply { addAll(items) }` copies the selection because the tagging screen mutates its list — passing the ViewModel's live list would corrupt the selection.
- `context?.asFragmentActivity()` guards the tagging-screen launch (needs an activity, not just any context); a null context silently skips — correct during teardown, suspicious anywhere else.

