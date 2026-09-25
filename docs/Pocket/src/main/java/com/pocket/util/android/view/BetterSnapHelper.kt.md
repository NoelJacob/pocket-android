# Pocket/src/main/java/com/pocket/util/android/view/BetterSnapHelper.kt
## What this is
An abstract base class that makes writing a `RecyclerView` snap helper (snap = auto-align scrolling to item edges) simpler than the stock `SnapHelper`. Instead of hiding the `RecyclerView`, it hands it to subclasses, uses plain scroll/fling listeners, and caches the `OrientationHelper` (a measuring proxy for padded list edges).
## How it fits
Extended by `BetterPagerSnapHelper`, which implements the two `snap()` methods for the `CoverflowView` carousel. This class owns the listener lifecycle (`attach()`/`detach()`) and the orientation-helper cache; subclasses only compute distances and call `smoothScrollBy()`.
## Key pieces
- `BetterSnapHelper(recyclerView)` — WHY: keeps the list reference visible to subclasses, unlike the support base class. Usage in words: subclass it, passing the target RecyclerView.
- `attach()` / `detach()` — WHY: wires and unwires the scroll listener plus fling listener, with an initial `snap()` so the list starts aligned. Usage in words: call `attach()` in setup, `detach()` when the view is torn down.
- `snap()` (abstract) — WHY: the settle contract; subclasses implement edge-alignment here.
- `snap(velocityX, velocityY)` — WHY: optional fling override; defaults to plain `snap()`.
- `getHorizontalOrientationHelper()` + `OrientationHelperCache` — WHY: avoids recreating the measuring helper on every scroll; rebuilds only when the layout manager instance changes.
## Junior notes
- The idle detector needs real movement first (`hasScrolled` flag), so programmatic `scrollToPosition` without pixel deltas will not trigger a snap.
- Setting `onFlingListener` overwrites any existing one; only one snap helper (or fling listener) can own a RecyclerView at a time.
- `internal` visibility means subclasses and callers must live in the same module.

