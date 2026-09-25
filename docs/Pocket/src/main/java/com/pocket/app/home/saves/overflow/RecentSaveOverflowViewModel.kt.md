# Pocket/src/main/java/com/pocket/app/home/saves/overflow/RecentSaveOverflowViewModel.kt
## What this is
This is the ViewModel for the overflow bottom sheet on one of the user's own recent saves (the "..." menu). It offers Share, Mark as viewed, Archive, and Delete for that item. A bottom sheet is a panel that slides up from the bottom of the screen.
## How it fits
`RecentSavesOverflowFragment` passes the tapped `Item` and its position via `onInitialized()` (plain field assignment — no flow needed since it never changes). Each action calls `ItemRepository` (`toggleViewed`, `archive`, `delete(item)` overload) and emits `Dismiss` so the sheet closes; Share emits `ShowShare` so the fragment can open `ShareDialogFragment`. Note this is separate from `RecommendationOverflowBottomSheetViewModel`, which handles recommended (not-yet-saved) stories and offers Report instead.
## Key pieces
- `onMarkAsViewedClicked()` — toggles viewed state, then dismisses; the fragment pre-labels the row "Mark as viewed" vs "Mark as not viewed" from `item.viewed`.
- `onShareClicked()` — emits `ShowShare` without dismissing directly; the fragment shares first, then dismisses.
- `Event` (`ShowShare` / `Dismiss`) — minimal: the sheet either opens the share dialog or closes.
- `RecentSaveOverflowInteractions` — the interface the databound XML calls.
## Junior notes
- `index` is stored but never used here — it's kept for analytics parity with other overflow menus; don't delete it thinking it's dead without checking analytics.
- Repository calls are fire-and-forget (no coroutine/exposed loading state) — the underlying list flow re-emits and the UI updates itself.

