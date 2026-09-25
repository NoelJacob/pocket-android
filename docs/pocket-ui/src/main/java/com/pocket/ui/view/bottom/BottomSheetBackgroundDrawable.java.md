# pocket-ui/src/main/java/com/pocket/ui/view/bottom/BottomSheetBackgroundDrawable.java

## What this is
The painted background of a bottom sheet: a card with rounded top corners (16dp radius), square bottom corners, and a soft top shadow. The user perceives the sheet as a floating rounded panel lifting off the content behind it. It fills whatever bounds it is given and follows the app theme.

## How it fits
`BottomDrawer.inflate()` installs this as the sheet container's background (`content.setBackground(...)`); it cannot be an XML shape because XML shapes cannot reference themed colors. It resolves the `pkt_bg` themed background color through `NestedColorStateList` and repaints itself on state changes.

## Key pieces
- Constructor: builds a `RoundRectShape` with top-only rounding, resolves the themed fill colors, and configures a shadow layer (`0px -3px 6px rgba(0,0,0,0.06)` equivalent) to fake a top-only box shadow.
- `onStateChange(int[])`: re-resolves the fill color for the new state and invalidates only when it actually changed; `isStateful()` returning `true` is what routes state to it.
- `onBoundsChange(Rect)`: resizes the internal shape to the new bounds so the background always fills the sheet.
- `draw(Canvas)`: translates down by the shadow size (so the shadow renders above the sheet edge) then draws the shape.

## Junior notes
- A custom `Drawable` must implement `draw`, `setAlpha`, `setColorFilter`, and `getOpacity`; stateful ones additionally override `isStateful`/`onStateChange`.
- `setShadowLayer` only works reliably with software rendering on some devices; if the shadow ever disappears on a hardware-accelerated view, that is the first suspect.
