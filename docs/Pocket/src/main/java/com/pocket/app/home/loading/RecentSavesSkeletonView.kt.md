# Pocket/src/main/java/com/pocket/app/home/loading/RecentSavesSkeletonView.kt
## What this is
This is the grey placeholder shown in the Home "Recent Saves" row while the user's saves load. It's a `ThemedConstraintLayout2` (theme-aware `ConstraintLayout`) wrapping the static `ViewHomeRecentSavesSkeletonBinding` layout — pure placeholder, no data.
## How it fits
Home's layout stacks this view under the real recent-saves `RecyclerView`; `RecentSavesViewModel.ScreenState` exposes `recentSavesLoadingVisible`, which the XML binds to this view's visibility. Unlike the slate/details skeletons there is only one layout variant (the recent-saves row looks the same on phones and tablets).
## Key pieces
- `binding` (public val) — the inflated skeleton binding is exposed, though nothing currently mutates it; it exists for symmetry with other card views.
- `setVisibility()` override — 1-second alpha fade-in on becoming VISIBLE, matching the other skeletons so all Home loading states feel consistent.
## Junior notes
- Keep the fade duration (1000ms) in sync with `DetailsSkeletonView`/`SlatesSkeletonView` — they were tuned together to mask the skeleton-to-content swap.
- Don't put loading logic here; the ViewModel decides when this shows.

