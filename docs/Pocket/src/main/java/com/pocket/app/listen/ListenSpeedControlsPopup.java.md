# Pocket/src/main/java/com/pocket/app/listen/ListenSpeedControlsPopup.java

## What this is
The small floating speed-picker bubbleanchored to the Listen speed button: current speed ("1.5x") flanked by minus/plus steppers.

## How it fits
Created by `ListenControlsView` (and the full player) with fixed width/height from dimens; the host sets the label via `setSpeed` and handles plus/minus through listener setters that step the playback speed. It is a `PopupWindow` (a floating overlay window), dismissed by tapping outside.

## Key pieces
- `setSpeed(text)` — WHY: refreshes the label whenever `bind` runs so the bubble never shows a stale speed.
- `setOnPlusClickListener` / `setOnMinusClickListener` — WHY: the popup owns no playback logic; the host applies clamped `setSpeed` calls.
- `Background` drawable — WHY: custom rounded-bubble background with shadow drawn in code to match the player theme across densities.
- `setFocusable(true)` + `setCancelOnOutsideTouch` — WHY: back button and outside taps dismiss without extra code.

## Junior notes
- `showAsDropDown(anchor, xOff, yOff)` positions relative to the speed button; offsets are precomputed from the popup's fixed size so it centers above the button — changing the dimens without the offsets misaligns it.
- `PopupWindow` content must have a background drawable or outside-touch dismissal silently stops working on some OS versions; that is why `Background` exists.
