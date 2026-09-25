# Pocket/src/main/java/com/pocket/util/android/view/BetterPagerSnapHelper.kt
## What this is
A pager-style snap helper for horizontal `RecyclerView` lists (scrollable lists that recycle row views): when scrolling settles or a fling ends, it smooth-scrolls so one card edge aligns with the list edge, page by page. It exists because the stock support-library pager misbehaved on tablets.
## How it fits
Attached to the cover-flow carousel in `com.pocket.app.listen.CoverflowView` (in words: create it with the RecyclerView, then call `attach()`). It extends `BetterSnapHelper`, reusing the scroll/fling listener plumbing and orientation-helper cache, and drives movement via `smoothScrollBy()`. It produces scroll deltas only.
## Key pieces
- `snap()` (no velocity) — WHY: settle behavior; measures the first two visible children plus the end edge when the last item shows, then scrolls the smallest distance so the nearest edge wins.
- `snap(velocityX, velocityY)` — WHY: fling behavior; negative velocity snaps left, otherwise snaps right, giving directional paging.
- `snapToLeft()` / `snapToRight()` — WHY: one-directional settle helpers; right also considers the list's end padding so the last card flush-aligns instead of overshooting.
- `getStartSnapDistance(view)` / `getEndSnapDistance(view)` — WHY: pixel math against the orientation helper's padded edges.
- `isLastChild(view)` — WHY: detects the adapter's final item so end-alignment only applies there.
## Junior notes
- Horizontal only; attaching it to a vertical list silently computes wrong distances.
- Guards null adapter/layout and tiny child counts by doing nothing, so `attach()` before the adapter is set is safe.
- `RecyclerView` allows only one `onFlingListener`; attaching this replaces any previous fling handler.

