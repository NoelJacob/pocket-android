# pocket-ui/src/main/java/com/pocket/ui/view/bottom/BottomSheetDragHandle.java

## What this is
The small horizontal grabber pill centered at the top of a bottom sheet that tells the user "drag me". It renders as a short rounded bar (default 70dp wide, 6dp tall, in themed grey-5) and is purely visual; dragging is handled by the sheet behavior, not by this view.

## How it fits
Sheet layouts (like the `BottomDrawer` chrome) include a `BottomSheetDragHandle` with `wrap_content` size at the top of the sheet content. It extends `ThemedView` so its grey color follows the app theme, and it draws itself in `onDraw()` with no child views.

## Key pieces
- `getSuggestedMinimumWidth/Height()`: define the standard 70x6dp size (plus padding); explicit `layout_width/height` values override these, so sheets needing a bigger or smaller handle just set dimensions.
- `onMeasure(...)`: resolves the measured size against the parent constraints with a local `getSuggestedSize` (EXACTLY wins, AT_MOST clamps, UNSPECIFIED uses the default) and caches the padded draw `bounds`.
- `onDraw(Canvas)`: re-resolves the themed handle color for the current state, computes the pill radius as half the smaller dimension, and draws one round rect.
- `updatePaint(int[])`: picks the state-matching grey from the color list, falling back to transparent.

## Junior notes
- `ThemedView` is the pocket-ui base that refreshes themed colors on theme change; custom drawing views extend it instead of stock `View`.
- Padding is part of the measured size but excluded from the drawn bar via `bounds`; that is why the bar shrinks inside padded handles rather than painting under the padding.
