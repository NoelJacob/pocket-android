# Pocket/src/main/java/com/pocket/app/home/slates/overflow/RecommendationOverflowBottomSheetFragment.kt
## What this is
This is the overflow bottom sheet for a recommended story (one the user hasn't saved yet): a "..." menu with Share and "Report this item" rows. It is the recommendation-side counterpart to `RecentSavesOverflowFragment`, which handles the user's own saves.
## How it fits
Shown from `DetailsFragment` (and Home) via `newInstance(url, title, corpusRecommendationId)`, passing data through plain fields. Button taps are databound to `RecommendationOverflowBottomSheetViewModel`; its one-shot events drive navigation: `ShowShare` opens `ShareDialogFragment` with the URL/title then dismisses, `ShowReport` opens `ReportItemBottomSheetFragment` then dismisses.
## Key pieces
- `newInstance(...)` — field-based factory (`url!!` crashes on null, so callers must pass a real URL; `title ?: ""` tolerates a missing title).
- `setupEventObserver()` — collects `viewModel.events` while RESUMED; both branches `dismiss()` after launching the next dialog so sheets never stack.
- Databinding (`binding.viewModel = viewModel` + `lifecycleOwner`) — the XML rows call `onShareClicked()`/`onReportThisItemClicked()` directly, so there's no click-listener code here.
## Junior notes
- Uses `parentFragmentManager` (not `childFragmentManager`) when showing the next dialog — correct, because this sheet dismisses itself and a child dialog would die with it.
- Like the other overflow sheets, fields don't survive process death; Safe Args would, but bottom sheets here uniformly use the field pattern.

