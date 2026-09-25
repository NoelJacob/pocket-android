# pocket-ui/src/main/java/com/pocket/ui/view/button/OnColorButton.kt

## What this is
A box button designed to sit on top of a colored background (e.g. a teal header or hero panel). For the user it looks like a white pill with text punched out so the background color shows through the letters — a "cutout" effect that keeps the label readable no matter what sits behind it. It is `internal` (visible only inside the pocket-ui module), so app code never references it directly; it is used by pocket-ui's own colored containers.

## How it fits
The fourth `BoxButtonBase` subclass alongside `BoxButton`, `ErrorButton`, and `UpgradeButton`, but with custom drawing instead of a plain text color: it paints a white `ButtonBoxDrawable` (`pkt_button_box_oncolor_fill`) background, then draws the inherited black label text through `cutoutFromCanvas` (a helper that erases instead of painting, knocking the text shape out of the white background). Pressed feedback comes from halving the background alpha rather than swapping colors.

## Key pieces
- `mBackground` — the white box drawable, with its `callback` wired to the view; WHY the callback: drawables use it to request redraws on state change, and without it pressed/disabled transitions would not repaint.
- `init()` setting black text — WHY black when the user sees background-colored text: the text is only a mask for the cutout operation; its own color never reaches the screen, but it must be opaque for the erase to work.
- `onSizeChanged(...)` — resizes `mBackground` to the view bounds; WHY overridden: unlike siblings that use `setBackgroundDrawable` (auto-sized by the framework), this drawable is drawn manually in `onDraw` so it must be measured by hand.
- `drawableStateChanged()` — halves `mBackground` alpha while pressed; the simple press feedback for a button whose "color" is whatever is behind it.
- `onDraw(canvas)` — draws the white background first, then the base-class text inside a cutout block; ordering matters — background first, erase-text second.

## Junior notes
- `cutoutFromCanvas` relies on Porter-Duff-style erase blending — placing this button over anything that doesn't support hardware-layer cutouts (very old APIs, some animations) can show a black box instead of transparency; test on-color placements on device.
- Because it is `internal`, adding a new usage outside pocket-ui means either exposing it or (more likely) reconsidering whether a plain `BoxButton` works there.
