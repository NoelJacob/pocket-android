# Pocket/src/main/java/com/pocket/app/home/saves/overflow/RecentSavesOverflowFragment.kt
## What this is
This is the overflow bottom sheet UI for one of the user's recent saves — the "..." menu with Share, Mark as viewed, Archive, Delete rows. It is a thin view layer: the layout is databound to `RecentSaveOverflowViewModel`, and this file only forwards the item in, reacts to events, and sets the viewed-row label.
## How it fits
Shown by Home when `RecentSavesViewModel` emits `ShowSaveOverflow`, constructed via `newInstance(item, itemPosition)` (plain field assignment, not fragment arguments — so it does not survive process death; that's a known shortcut). `ShowShare` opens `ShareDialogFragment` with the item's domain model and dismisses; `Dismiss` just closes. The mark-as-viewed row is relabeled in `setupMarkAsViewed()` because its text/icon depend on current state, which databinding alone doesn't cover.
## Key pieces
- `newInstance(item, itemPosition)` — factory that stuffs fields directly; simple but lost on process recreation (compare Safe Args fragments elsewhere).
- `setupMarkAsViewed()` — swaps text (`ic_mark_as_viewed` vs `ic_mark_as_not_viewed` strings) and icon (`ic_viewed` vs `ic_viewed_not` drawables) based on `item.viewed == true`.
- `setupEventsObserver()` — collects `viewModel.events` while RESUMED; share uses `parentFragmentManager` so the share dialog outlives this sheet's dismissal.
- `onDestroyView()` nulls `_binding` — standard fragment view-leak guard.
## Junior notes
- `AbsPocketBottomSheetDialogFragment` is the shared base giving Pocket-styled bottom-sheet behavior (drag to dismiss, rounded top); don't fight it with custom window flags.
- `item.toDomainItem()` converts the server `Item` to the UI/share-friendly model — the share dialog needs the domain shape, not the raw API type.

