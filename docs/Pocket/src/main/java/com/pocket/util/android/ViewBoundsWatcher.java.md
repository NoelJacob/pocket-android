# Pocket/src/main/java/com/pocket/util/android/ViewBoundsWatcher.java

## What this is
A tracker that fires a callback whenever a view's on-screen position, size, or visibility changes — for example when scrolling moves an anchor view under a popup. It solves the "popup/tooltip no longer points at its anchor" problem by reporting absolute bounds so the owner can reposition itself. For example, `Tooltip` watches its anchor view and re-runs `applyAnchor(anchor)` on the UI thread whenever the bounds shift.

## How it fits
It is created via the static `create(view, listener, isScrollable)` factory, typically by floating UI that must track an anchor; the known consumer is `Tooltip` (tooltip positioning). It subscribes to the view tree's scroll/layout/attach/layout-change signals and reports through `OnViewAbsoluteBoundsChangedListener`. It is one-shot by design: `stop()` permanently detaches it and a fresh instance is needed afterwards.

## Key pieces
- `create(view, listener, isScrollable)`: nullable factory that returns null when there is no usable `ViewTreeObserver`. WHY nullable: detached or dying views cannot be watched, and callers must handle that.
- `stop()`: permanently unregisters everything. WHY one-way: avoids half-detached listener leaks; watchers are cheap to recreate.
- `invalidateVisibilityAndBounds()` / `invalidateBounds()`: compare current global-visible-rect against the last snapshot and notify on change. WHY two levels: visibility flips and geometry moves are distinct events with different handling.
- `Callbacks` (scroll-changed + attach-state + layout-change + global-layout listener set): the subscription bundle. WHY bundled: bounds can change via layout, scroll, or window attach, and missing any source means missed moves.
- `OnViewAbsoluteBoundsChangedListener`: the callback receiving the new left/top/right/bottom. WHY absolute coordinates: popups position in window space, not parent space.

## Junior notes
- Global listeners are registered only while the view is attached to a window (a perf optimization); expect no callbacks for detached views rather than stale ones.
- It tracks hard layout/scroll changes only — animations and view transforms (scale/translate/alpha) do NOT trigger it, per the class Javadoc.
- Callbacks can arrive mid-layout; `Tooltip` reposts its repositioning to the handler to apply changes safely after the pass.
