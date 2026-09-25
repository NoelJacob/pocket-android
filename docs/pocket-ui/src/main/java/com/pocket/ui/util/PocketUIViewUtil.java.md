# pocket-ui/src/main/java/com/pocket/ui/util/PocketUIViewUtil.java

## What this is
Two one-off view-tree operations that have no stock convenience call: swapping a view for another view in the same parent position, and running a block once after a view's next layout pass. Both exist to stop screens from hand-rolling parent-index bookkeeping and layout listeners.

## How it fits
Screens and custom containers call `replaceView` when a slot's content type changes at runtime (for example exchanging a loading view for real content) while keeping position and sizing. They call `runAfterNextLayoutOf` when follow-up work needs final measured coordinates, such as scrolling to or measuring a freshly laid-out child.

## Key pieces
- `replaceView(replace, replacement)`: removes the old view, copies its layout params onto the replacement (so sizing rules survive the swap), and inserts the replacement at the same index. Copying params and preserving the index is the whole point; a naive remove-and-add would reset sizing and ordering.
- `runAfterNextLayoutOf(view, block)`: adds a one-shot `OnLayoutChangeListener` (a callback Android fires after a view is measured and positioned) that runs the block on the next layout and immediately unregisters. Self-removal is what makes it fire exactly once instead of on every future layout.

## Junior notes
- `replaceView` casts the parent to `ViewGroup` and dereferences it without checks, so the old view must already be attached. Calling it on a parentless view crashes.
- `runAfterNextLayoutOf` only fires if a layout actually happens. If the view never re-lays-out, the block never runs and the listener lingers, so do not use it for work that must run unconditionally.
