# pocket-ui/src/main/java/com/pocket/ui/view/info/PageIndicatedViewPager.java
## What this is
A reusable swipeable pager with "you are on page 2 of 5" dots underneath: a `ViewPager2` stacked above a `PageIndicatorView`. It keeps the dots in sync with swipes automatically, and swipes that start on the dot area still page. It works with any `RecyclerView.Adapter`, not just info pages.
## How it fits
Embedded inside `InfoPagingView` (which supplies an `InfoPageAdapter`) but usable anywhere a dot-indicated pager is needed. The host calls `bind().adapter(...)` with its adapter; `adapter.getItemCount()` drives how many dots appear. Hosts add their own `ViewPager2.OnPageChangeCallback` (a listener object that fires when the visible page changes) via `addOnPageChangeListener()` for extra per-page work.
## Key pieces
- `indicatorListener` — internal page-change callback that sets `indicators.bind().currentIndex(position)` on every swipe. WHY: the reason dots track the pager with zero host code; it is re-added whenever listeners are cleared so dots never silently stop working.
- `Binder.adapter(RecyclerView.Adapter)` — sets the pager adapter, sets dot count from `getItemCount()`, and hides the dots when there are fewer than 2 pages. WHY: a single dot (or none) is visual noise, so the view hides itself in that case.
- `Binder.nextPage()` / `previousPage()` / `setPage(int)` — programmatic navigation with bounds checks returning whether the page actually moved. WHY: Back/Next buttons need to know if they hit an end.
- `Binder.add/clearOnPageChangeListener(...)` — listener bookkeeping around `pager.registerOnPageChangeCallback`. WHY: `ViewPager2` requires manual unregistering to avoid leaks; `clear` always re-registers the dot listener so clearing custom listeners cannot break the dots.
- `onTouchEvent()` override — forwards touches on the indicator strip to the pager's inner `RecyclerView`. WHY: without this, drags starting on the dots would be swallowed and feel dead.
## Junior notes
- `init()` calls `pager.getChildAt(0)` to reach `ViewPager2`'s internal `RecyclerView` (to disable overscroll glow). That child only exists after the layout is inflated — calling `bind()` before the view is attached can hit a null child.
- `ViewPager2` page callbacks fire for every intermediate page during a fling; keep custom `OnPageChangeCallback` work cheap or the dots and button updates will lag.
