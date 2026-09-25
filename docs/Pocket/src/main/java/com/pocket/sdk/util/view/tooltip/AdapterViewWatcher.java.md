# Pocket/src/main/java/com/pocket/sdk/util/view/tooltip/AdapterViewWatcher.java
## What this is
A tracker that follows one item inside a scrolling list (`AdapterView`, Android's list view that recycles row views) so an anchored tooltip can move with it. It watches layout and scroll events, re-finds the row view for the item, and reports when the view instance changes. Tooltips anchored to list rows stay glued to the right row while scrolling.
## How it fits
Created by `Tooltip.TooltipController.anchorAdapterItem()` when a tooltip targets a list item rather than a fixed view; its `Listener` re-anchors the tooltip on every row change. `findView()` is also used statically for the initial lookup before the watcher starts. `stop()` is called when the tooltip dismisses or re-anchors.
## Key pieces
- `AdapterViewWatcher(item, adapterView, view, listener)` — snapshots the known row and subscribes a layout/scroll observer. WHY: any scroll or relayout can recycle the row into a different view object.
- `invalidate()` — re-runs the lookup and fires `onAdapterItemViewChanged()` only when the view instance actually changed. WHY: avoids re-anchoring (and flicker) on scrolls that did not move this item.
- `findView(item, adapterView)` — scans visible children, mapping child index plus first-visible-position to adapter items by equality. WHY: recycled row views cannot be cached; identity must be re-resolved each layout.
- `stop()` — detaches the layout observer. WHY: the observer holds the list view, so leaking it pins the whole screen.
- `Listener.onAdapterItemViewChanged(view)` — delivers the new row view (possibly null when scrolled off). WHY: the controller decides whether to move, retry, or give up.
## Junior notes
- Item equality (`equals()`) is load-bearing here; items without value equality never match their row and the tooltip immediately fails to show.
- A null view means the item scrolled out of sight, not an error; the controller's retry/give-up logic handles that case.
