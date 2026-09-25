# pocket-ui/src/main/java/com/pocket/ui/view/menu/SettingIncrementor.java

## What this is
A single reader-setting stepper row: a minus button, a setting icon, and a plus button in one line. The user sees the icon (e.g. text size) flanked by -/+ controls; tapping them steps the value up or down. It displays no value itself — the host owns the actual setting.

## How it fits
Inflated from `R.layout.view_setting_incrementor` and embedded three-or-more at a time in `DisplaySettingsView` (text size, line height, margins — with text size existing in both free and Premium variants). `DisplaySettingsView.Binder` wires each row via `bind()`: assigning the icon, the accessibility label, the -/+ listeners, and whether each button is currently enabled (e.g. minus disabled at minimum size).

## Key pieces
- `SettingIncrementor` — extends `ThemedConstraintLayout`; `init()` inflates the layout, grabs the `up`/`down`/icon views, and calls `bind().clear()`.
- `bind()` / `Binder` — the full API; `clear()` nulls both listeners, zeroes the icon, re-enables both buttons, and clears the label.
- `Binder.upListener` / `downListener` — tap handlers for the + and − buttons (null removes the handler).
- `Binder.icon(@DrawableRes)` — the middle icon describing the setting.
- `Binder.upEnabled` / `downEnabled` — grey out a button at the range limit.
- `Binder.label(@StringRes)` — does not show text; it builds the -/+ buttons' accessibility descriptions from the setting name via the `setting_incrementor` / `setting_decrementor` templates (0 clears them).

## Junior notes
- The naming is from the buttons' perspective: `up` = plus/increase, `down` = minus/decrease — easy to mix up since the minus button usually sits on the left.
- Accessibility descriptions (TalkBack labels) are the main role of `label()` — if steppers announce nothing, this call was skipped, not the icon.
