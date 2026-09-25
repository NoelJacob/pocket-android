# pocket-ui/src/main/java/com/pocket/ui/util/OnlyWhenVisibleHelper.java

## What this is
A one-call helper that runs setup code when a view becomes genuinely visible and teardown code when it stops being visible. "Visible" here is strict: attached to a window, itself and all parents `VISIBLE`, and non-zero measured size. It removes the boilerplate of combining attach listeners with layout listeners in every animated or resource-holding view.

## How it fits
Views that animate, observe data, or hold expensive resources call `install(view, onVisible, onHidden)` once. Downstream, `onVisible` starts work (restart an animation, subscribe) and `onHidden` stops it (cancel the animation, release the resource), including the case where the view scrolls off screen while staying attached.

## Key pieces
- `install(view, onVisible, onHidden)`: the whole API. Fires the matching callback immediately for the current state, then on every transition. Null callbacks default to no-ops so callers can pass only the side they need.
- `onViewAttachedToWindow` / `onViewDetachedFromWindow`: subscribes and unsubscribes the global-layout listener with the view's `ViewTreeObserver` (a per-view-tree event source for layout changes). Wiring through attach state is what avoids leaking a dead observer after the view leaves the window.
- `onGlobalLayout`: re-evaluates visibility on every layout pass, which catches scrolling, resizing, and parent visibility changes that attach state alone would miss.
- `update()`: the visibility test (`isAttachedToWindow && isShown && width > 0 && height > 0`) with first-run and change-only firing. The change-only guard is why callbacks do not spam on every layout while visibility is stable.

## Junior notes
- Visibility requires non-zero size, so a view that has not been measured yet counts as hidden and gets `onHidden` first. Do not assume `onVisible` runs before first layout.
- The helper holds the view and both runnables for as long as the attach listener is registered. If the view outlives its screen (for example cached in a pool), the callbacks keep whatever they capture alive too.
