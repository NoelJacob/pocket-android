# Pocket/src/main/java/com/pocket/util/android/view/TouchShiftFrameLayout.java
## What this is
A `FrameLayout` (a view group stacking children) that keeps touch coordinates stable when its own top edge moves mid-gesture. If a relayout shifts the layout while the finger is down, it offsets incoming touch events by the top delta so scrolling does not jump.
## How it fits
Currently has no live usages in layouts or code; it is legacy support presumably once wrapping scroll content whose header resized mid-scroll. It is self-contained: it intercepts touches in `onInterceptTouchEvent()` (the pass where a parent can steal touches from children), shifts them, and passes them on. It only borrows `ScrollTracker.DEBUG` for logging and `MotionUtil` for log formatting.
## Key pieces
- `onInterceptTouchEvent(ev)` — WHY: the whole feature; records top on DOWN, offsets by `getTop() - mTopAtDown` while tracking, stops on UP/CANCEL. Usage in words: wrap relayout-prone scroll content in this layout and touches stay anchored automatically.
- `mIsTracking` / `mTopAtDown` — WHY: gesture-scoped state so only the in-progress touch stream is shifted.
## Junior notes
- It mutates the event with `offsetLocation()` before delegating to super; children see already-corrected coordinates and must not re-offset.
- Only vertical top shifts are compensated; horizontal moves or size changes without a top change pass through untouched.
- Returning `super.onInterceptTouchEvent(ev)` preserves default interception; this class never steals touches, it only rewrites coordinates.

