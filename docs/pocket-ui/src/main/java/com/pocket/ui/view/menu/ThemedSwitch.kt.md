# pocket-ui/src/main/java/com/pocket/ui/view/menu/ThemedSwitch.kt

## What this is
Pocket's standard on/off switch: the stock `SwitchCompat` toggle pre-tinted with Pocket's thumb and track colors so it matches light/dark themes. The user sees the familiar sliding thumb; on/off colors follow the app theme automatically.

## How it fits
Used in XML layouts anywhere a settings toggle appears (e.g. settings screens, reader options). Because styling happens in `init`, layout authors just drop in `<com.pocket.ui.view.menu.ThemedSwitch>` instead of styling each `SwitchCompat` by hand. It behaves exactly like `SwitchCompat` otherwise — `isChecked` listeners and databinding work unchanged.

## Key pieces
- `ThemedSwitch` — extends `SwitchCompat`; `init` tints the thumb with `pkt_switch_thumb` and the track with `pkt_switch_track` via `NestedColorStateList` (Pocket's theme-aware color resolver).
- `onCreateDrawableState` — merges Pocket's app-theme state (`AppThemeUtil.getState`) into the drawable state, so the tint lists re-resolve on theme change.
- `setOnClickListener` override — pure pass-through to super; no behavior change.

## Junior notes
- `DrawableCompat.setTintList` tints the existing thumb/track drawables in place — it does not replace them, so the Material switch animation and ripple are preserved.
- Merging theme state in `onCreateDrawableState` is the standard trick for theme-aware tints: without it the switch would keep its old colors after a light/dark toggle until recreated.
