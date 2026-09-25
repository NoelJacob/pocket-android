# Pocket/src/main/java/com/pocket/app/list/list/MyListPagingScrollListener.kt

## What this is
A scroll listener that triggers loading the next page of saves just before the user reaches the bottom of the list, so scrolling feels endless.

## How it fits
Attached to the My List RecyclerView alongside `MyListAdapter`. On every scroll it checks how close the last visible row is to the end and calls `MyListViewModel.onScrolledNearBottom()`, which forwards to `ListManager.loadNextPage()` and its `SyncCache` paging.

## Key pieces
- `onScrolled` — WHY: computes `lastVisiblePosition` via `LayoutManagerUtil` and fires when within `PAGING_ITEM_THRESHOLD` rows of the end.
- `PAGING_ITEM_THRESHOLD = 20` — WHY: prefetch margin; loading starts 20 rows early so the next page usually arrives before the user sees a spinner.

## Junior notes
- This fires repeatedly while near the bottom — the downstream `loadNextPage` must be idempotent (it is; `SyncCache` ignores redundant loads). Do not add your own debounce here.
- It needs the adapter's `itemCount`, so attach it only to the RecyclerView this adapter serves.
