# Pocket/src/main/java/com/pocket/app/list/tags/TagBottomSheetFragment.kt

## What this is
The tag-picker bottom sheet on the Saves list: it lists the user's tags (recently used first) plus a "not tagged" row, and supports renaming and deleting tags from an overflow menu.

## How it fits
Opened from the list toolbar via `newInstance(savesTab)`. The fragment is a thin shell over `TagBottomSheetViewModel`: it sets up the `TagsAdapter` RecyclerView, wires overflow/cancel/save buttons, and observes `navigationEvent` to dismiss or show the delete-confirmation dialog. Picking a tag calls into `ListManager.setTag`, which refilters the main list behind the sheet.

## Key pieces
- `setupRecyclerView` — WHY: attaches `TagsAdapter`, which renders tag rows plus the special not-tagged row from ViewModel state.
- `setupClickListeners` — WHY: overflow opens the rename-mode popup (`onEditClicked`); cancel/save exit or commit rename mode, hiding the keyboard first.
- `setupScreenStateListener` — WHY: reacts to one-shot events: `Close` dismisses, `ShowConfirmDelete` shows the `AlertMessaging` confirm dialog and forwards the choice.
- `onDismiss` → `viewModel.onDismissed()` — WHY: if the user leaves without picking a tag, clears the transient filter so the list does not stay empty.
- `newInstance(savesTab)` — WHY: bundles the tab enum for rotation-safe restore.

## Junior notes
- Rename mode is ViewModel state (`cancelVisibility`), not fragment state — rotation mid-rename preserves the in-progress edits map.
- The delete confirmation uses `AlertMessaging.show`, the shared themed dialog helper; pass `tagToDelete` into the message so the user sees which tag is doomed.
