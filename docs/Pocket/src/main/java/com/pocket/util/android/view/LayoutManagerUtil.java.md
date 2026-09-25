# Pocket/src/main/java/com/pocket/util/android/view/LayoutManagerUtil.java
## What this is
A static helper that answers "which item is first/last visible?" for any common `RecyclerView.LayoutManager` (the object that positions list rows: linear or grid). It hides the `GridLayoutManager` vs `LinearLayoutManager` type check behind one call.
## How it fits
Used by `com.pocket.app.list.list.MyListPagingScrollListener`, which triggers the next page load when the last visible position nears the end (in words: `findLastVisibleItemPosition(recyclerView)`). It consumes a RecyclerView or layout manager and returns an adapter position.
## Key pieces
- `findFirstVisibleItemPosition(view)` / `(layout)` — WHY: scroll-up detection and restore logic without duplicating instanceof checks.
- `findLastVisibleItemPosition(view)` / `(layout)` — WHY: infinite-scroll trigger point. Usage in words: compare the result against `adapter.itemCount` to decide when to fetch more.
## Junior notes
- Unknown layout types (e.g. `StaggeredGridLayoutManager`) throw `RuntimeException`; add a branch before using this with exotic layouts.
- The `(layout)` overload with a null manager returns 0 for first-visible; the last-visible overload would NPE on null, so prefer the `(view)` forms or null-check first.

