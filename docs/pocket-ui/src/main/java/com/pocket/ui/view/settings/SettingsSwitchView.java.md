# pocket-ui/src/main/java/com/pocket/ui/view/settings/SettingsSwitchView.java
## What this is
A standard settings row: a title, an optional subtitle, and an optional toggle switch on the right. Tapping anywhere on the row flips the switch. With the switch hidden (`isToggle=false`) it doubles as a plain action row that navigates somewhere.

## How it fits
Settings screens list these rows (layout view_settings_switch) for on/off preferences like notifications or dark mode. XML attributes supply `android:title`, `android:text` (subtitle, hidden when empty), `android:enabled`, and `isToggle`. The Binder drives it in code: `title()`, `subtitle()`, `checked()`, `enabled()`, `onCheckedListener()`, `onClickListener()`. It extends VisualMarginConstraintLayout so row spacing is visually exact.

## Key pieces
- `init()` — inflates the layout, grabs title/subtitle/switch, makes row taps toggle the switch, enforces a 72dp minimum height and a touchable background.
- `setEnabled()` — propagates enabled state to all children via EnabledUtil so the whole row dims together.
- `setChecked()` / `isChecked()` / `toggle()` — thin pass-throughs to the inner ThemedSwitch.
- Inner `Binder` — `isToggle()` hides the switch for action rows, `subtitle()` hides the subtitle view when null, plus checked/enabled/listener setters and `clear()` defaults.

## Junior notes
- The default row click listener toggles the switch; calling `onClickListener()` in the Binder replaces it, so action rows must handle navigation there.
- `android:title` and `android:text` are borrowed standard attributes remapped to this view's fields, not real TextView properties of the row itself.
