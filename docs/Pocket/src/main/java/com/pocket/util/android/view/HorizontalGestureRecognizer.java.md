# Pocket/src/main/java/com/pocket/util/android/view/HorizontalGestureRecognizer.java
## What this is
A touch classifier that decides whether a gesture is a horizontal swipe (page turn) or a vertical scroll, and reports completed swipes. It waits until movement exceeds the system touch slop (the minimum drag distance before it counts as a scroll), measures the gesture angle, then absorbs subsequent touches so the underlying scroller stops fighting the page turn.
## How it fits
Currently has no live callers in the codebase; it is legacy paging support presumably once used by reader paging views. It is self-contained: a host view would feed every `MotionEvent` to `onTouchEvent()` and implement `SwipeListener` to receive `swiped(left)` callbacks. It produces boolean absorb decisions plus swipe events, with thresholds from `FormFactor.dpToPx`.
## Key pieces
- `HorizontalGestureRecognizer(viewContext)` — WHY: captures touch slop and swipe thresholds (15dp horizontal mode, 70dp otherwise). Usage in words: create once per swipeable view.
- `onTouchEvent(ev)` — WHY: the whole classifier; DOWN arms detection via `onHorizontalGestureDown`, MOVE/UP measures distance and angle. Returns true when the host should swallow the touch. Usage in words: call from the host's touch handler and honor the return value.
- `setHorizontalMode(enabled)` — WHY: makes swipes easier (15dp vs 70dp length, 73-degree vs 17-degree angle tolerance) when the content is known-horizontal.
- `SwipeListener` (`swiped(left)` / `isPagingEnabled()` / `onHorizontalGestureDown(x, y)`) — WHY: the host contract; gates detection per-gesture and per-location.
- `setListener(...)` / `isPaging()` — WHY: wiring plus a query for current horizontal mode.
## Junior notes
- On the first absorbed gesture it rewrites the event to `ACTION_CANCEL`; that cancels the in-progress scroll in the child view, which is intentional, not a bug.
- Returns false when no listener is set or paging is disabled, so an unwired instance safely passes all touches through.
- Angle math uses `atan2(dy, dx)` in degrees; near 0 or 180 means horizontal, which is why the check is `angle < sensitivity || angle > 180 - sensitivity`.

