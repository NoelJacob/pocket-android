# Pocket/src/main/java/com/pocket/app/home/slates/SlatesAdapter.kt
## What this is
This is the outer vertical adapter for the Home feed: one row per slate (curated topic row), each showing a title, optional subheadline, "see all" link, a large hero card (first story), and a nested strip of minor cards (the rest). It switches layouts for phones vs tablets.
## How it fits
Created by the Home screen with the `HomeViewModel` and an `isTablet` flag. Its `init` block collects `viewModel.slatesUiState` (scoped to CREATED) and submits to the `ListAdapter`. "See all" taps call `viewModel.onSeeAllRecommendationsClicked(position, title)`, which navigates to `SlateDetailsFragment`. Card taps/saves/overflow flow through `DefaultSlateViewHolderHelper` into the same `HomeViewModel`.
## Key pieces
- `getItemViewType()` — returns TABLET or NORMAL purely from the constructor flag (not from item content), choosing `SlatesTabletViewHolder` (`ViewHomeSlateWideBinding`, grid of minors + excerpt on hero) vs `SlateViewHolder` (`ViewHomeSlateDefaultBinding`, horizontal minor strip).
- `SlateViewHolder.bind()` / `SlatesTabletViewHolder.bind()` — set title/subtitle (hiding subtitle when null), wire "see all", bind story #1 to the hero card via the shared helper, and hand the remainder to their own `SlateMinorCardAdapter` via `setData(drop(1), title)`.
- `DIFF_CALLBACK.areItemsTheSame()` — slate IDs are unstable, so identity is a heuristic: same title OR same subheadline OR same recommendations counts as the same slate; the comment honestly notes a full simultaneous change of all three will animate as remove+insert.
## Junior notes
- Nested RecyclerViews (outer vertical + inner horizontal/grid) each have `itemAnimator = null` on the inner list — this avoids double-animation jank when the outer list updates.
- `state.recommendations.first()` will crash on an empty slate; slates are guaranteed non-empty upstream, but don't reuse this pattern without a guard.
- The tablet holder passes `excerpt` to the helper while the phone holder doesn't — that's why the helper's excerpt param is nullable.

