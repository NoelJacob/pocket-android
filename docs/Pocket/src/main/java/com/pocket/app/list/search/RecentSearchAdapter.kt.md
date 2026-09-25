# Pocket/src/main/java/com/pocket/app/list/search/RecentSearchAdapter.kt

## What this is
The small RecyclerView adapter that renders recent-search suggestion chips/rows under the Saves search bar.

## How it fits
Created by the My List search UI with `MyListViewModel` and the fragment's lifecycle owner. It re-renders whenever `viewModel.recentSearchState` emits, and tapping a row calls `viewModel.onRecentSearchClicked(text)`, which fills the search box and re-runs the query through `ListManager.setSearchText`.

## Key pieces
- `repeatOnCreated` collector on `recentSearchState` — WHY: refreshes the list on every emission; uses `notifyDataSetChanged` because the list is tiny and order churn makes diffing pointless.
- `recentSearches` getter — WHY: always reads the ViewModel's current value rather than caching, so binds never go stale.
- `RecentSearchViewHolder.bind(state)` — WHY: sets the row text and forwards taps with the raw query string.

## Junior notes
- `repeatOnCreated` (collect while the lifecycle is at least created) is enough here because suggestions must update even when the fragment is not resumed — do not "upgrade" it to `repeatOnResumed`.
- Taps go through the ViewModel rather than a listener lambda so analytics and search-history bookkeeping stay in one place.
