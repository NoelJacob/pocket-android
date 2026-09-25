# Pocket/src/main/java/com/pocket/util/android/MotionUtil.java

## What this is
A debug-logging helper that renders Android touch events (`MotionEvent`) as tiny readable strings like `{120,340, ACTION_MOVE}`. It solves the "touch bugs are invisible" problem: raw motion events are numeric codes, so logs need names. For example, `TouchShiftFrameLayout` logs `MotionUtil.motionEventToShortString(ev, true)` before and after shifting coordinates, but only when `ScrollTracker.DEBUG` is on.

## How it fits
It is a static-only helper used by touch-handling debug paths. Its known caller is `TouchShiftFrameLayout` (via `ScrollTracker` debug logging) for tracing how touch coordinates are offset during scrolls. It produces log strings only; it never touches event dispatch.

## Key pieces
- `motionEventToShortString(ev, round)`: formats position plus action name, with optional coordinate rounding; null-safe (`{null}`). WHY it exists: one-line touch traces in logcat.
- `motionEventActionToString(action)`: maps action codes to names, copied from Android's hidden `MotionEvent.actionToString`. WHY a copy: the framework method is hidden from the SDK, so the app reimplements it, including multi-touch `ACTION_POINTER_DOWN/UP(index)` decoding.

## Junior notes
- Multi-touch actions pack the finger index into the action int itself (pointer-index bits); this helper decodes and prints it, e.g. `ACTION_POINTER_DOWN(1)`.
- `round = true` truncates coordinates to ints for compact logs; keep full precision when diagnosing sub-pixel jitter.
- This is diagnostics-only code — never branch app behavior on these strings; match on the `MotionEvent` constants directly.
