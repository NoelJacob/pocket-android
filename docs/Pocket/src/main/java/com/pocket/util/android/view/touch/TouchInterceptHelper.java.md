# Pocket/src/main/java/com/pocket/util/android/view/touch/TouchInterceptHelper.java
## What this is
Decides when a touch stops being a tap and starts being a drag: it records the ACTION_DOWN point and returns true from `onInterceptTouchEvent()` once movement exceeds the system touch slop (the distance a finger may wander before it counts as a scroll). It can also record the event stream.
For example, a parent scroll container asks this on every move and steals the gesture once the finger drifts past slop.

## How it fits
A leaf helper any custom `ViewGroup` can embed in its `onInterceptTouchEvent` to separate taps from scrolls. Recording mode (`setRecordingEnabled`) buffers `MotionEvent`s for later replay or inspection by the owning view.

## Key pieces
- `TouchInterceptHelper(context)`: reads `ViewConfiguration.getScaledTouchSlop()`. WHY it exists: calibrates the drag threshold to screen density and user settings.
- `onInterceptTouchEvent(ev)`: DOWN anchors, MOVE compares, UP/CANCEL ends. WHY it exists: the tap-vs-drag verdict parents need before intercepting.
- `setRecordingEnabled(record)` + `getRecord()`: optional event buffering. WHY it exists: lets owners replay or analyze the gesture that led to interception.
- `clearRecord()` with `recycle()`: WHY it exists: returns pooled `MotionEvent` objects instead of leaking native event memory.

## Junior notes
- Touch slop is density-scaled pixels, not dp; never hardcode the threshold or drag feel breaks on different screens.
- Recorded events are `MotionEvent.obtain()` copies that must be recycled; `clearRecord()` does this, so do not hold the list past the next DOWN.
- Returning true means "parent steals the stream"; children then get ACTION_CANCEL, so child click state must tolerate cancellation.
