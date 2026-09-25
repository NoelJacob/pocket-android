# pocket-ui/src/main/java/com/pocket/ui/view/bottom/PktBottomSheetBehavior.java

## What this is
The Material bottom-sheet drag/state behavior with one Pocket addition: an optional touch gate that can temporarily disable sheet dragging. When the gate says no, the sheet ignores drag gestures but still settles to its normal state machine positions.

## How it fits
`BottomDrawer` attaches this behavior to its sheet container (via `BottomSheetBehavior.from(content)`) so the sheet can be dragged between hidden/collapsed/expanded. Screens that need to lock the sheet while some inner interaction (such as scrolling or an open control) owns the touches call `setTouchCondition()` with a check; the behavior consults it on every touch intercept.

## Key pieces
- `TouchCondition` interface (`canTouch()`): the lock check. WHY an interface and not a boolean: the lock state is dynamic (for example "locked while the inner list is open"), so the behavior polls the condition per gesture instead of storing a stale flag.
- `onInterceptTouchEvent(...)`: if no condition is set, or the condition allows touch, delegates to the stock Material behavior; otherwise still calls through (so internal state stays consistent) but returns `false`, declining to intercept the gesture.
- `setTouchCondition(TouchCondition)`: installs or replaces the gate; passing null restores always-draggable behavior.

## Junior notes
- `BottomSheetBehavior` is a `CoordinatorLayout.Behavior`: `onInterceptTouchEvent` returning true steals the touch stream for the sheet drag; returning false lets children or the content behind handle it.
- Both constructors (no-arg and `Context, AttributeSet`) are required: the XML-inflated one is used when the behavior comes from a layout file, the no-arg one when added in code.
