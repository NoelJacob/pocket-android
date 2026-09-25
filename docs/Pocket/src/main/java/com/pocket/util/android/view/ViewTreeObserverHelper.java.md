# Pocket/src/main/java/com/pocket/util/android/view/ViewTreeObserverHelper.java
## What this is
A lifecycle wrapper around a view's `ViewTreeObserver` (the framework object that broadcasts global layout and scroll events for a view hierarchy). It registers layout and scroll listeners in one call and removes both in `stop()`, with an enable flag to mute callbacks without unregistering.
## How it fits
Used by `com.pocket.sdk.util.view.tooltip.AdapterViewWatcher`, which watches an adapter-backed view to reposition tooltips on layout or scroll changes (in words: `new ViewTreeObserverHelper(adapterView, listener)` ... `stop()` on teardown). It consumes view-tree events and produces `Listener` callbacks.
## Key pieces
- `ViewTreeObserverHelper(view, listener)` — WHY: one-line subscription to both global-layout and scroll-changed events. Usage in words: construct with the watched view and a listener; keep the handle to stop later.
- `stop()` — WHY: unregisters both listeners if the observer is still alive and mutes further calls. Usage in words: call in `onDestroyView`/close paths to avoid leaks.
- `Listener` (`onGlobalLayout()` / `onScrollChanged()`) — WHY: the two forwarded events; layout means sizes/positions settled, scroll means something scrolled.
## Junior notes
- The observer snapshot is taken at construction; if the view is not yet attached, `getViewTreeObserver()` may return a dead observer, so construct after attachment.
- `stop()` is one-way; there is no restart, so create a fresh helper if you need to watch again.
- `mIsEnabled` starts true and `stop()` flips it false first, so even in-flight callbacks after `stop()` are suppressed.

