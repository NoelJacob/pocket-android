# pocket-ui/src/main/java/com/pocket/ui/view/menu/DisplaySettingsView.java

## What this is
The reader "Display / Text Settings" panel: theme picker, brightness slider with -/+ steppers, text-size/line-height/margin steppers, a change-font row, and a Premium upsell block. It hosts two parallel layouts — a simple text-size row for free users and full typography controls for Premium — and shows one or the other. It has built-in side and bottom padding.

## How it fits
Shown in the reader (article view) settings sheet; its layout is `R.layout.view_display_settings`. It composes `ThemeToggle` (theme picker) and several `SettingIncrementor` rows (text size, line height, margin). The reader screen or its ViewModel drives it entirely through `bind()` — setting brightness progress, wiring stepper click listeners, toggling `premiumSettingsVisible` / `premiumUpsellVisible`, and updating the font name/typeface.

## Key pieces
- `DisplaySettingsView` — root view, extends `ThemedConstraintLayout` (theme-aware layout); `init()` inflates the layout, grabs child views, and configures the brightness slider (max 100, delegating listener).
- `incrementBrightness(amt)` — helper behind the -/+ buttons; shifts slider progress by a fraction (±0.1) and clamps to 0–max.
- `bind()` / `Binder` — the only public API; fluent setters the host calls instead of touching child views.
- `Binder.clear()` — resets every row to a known default (icons, labels, listeners nulled, upsell shown, premium hidden).
- `Binder.theme()` — returns the inner `ThemeToggle.Binder` so the host configures the theme picker through this view.
- `Binder.brightness(float)` / `brightnessListener(...)` — sets slider position (0–1 fraction) and forwards seek events to the host.
- `fontSizeUpClick/DownClick`, `lineHeightUpClick/DownClick`, `marginUpClick/DownClick` + `...Enabled` variants — wire each `SettingIncrementor`'s -/+ buttons; font-size variants fan out to both the free and Premium rows.
- `fontChangeText` / `fontChangeTypeface` / `fontChangeClick` / `fontsLoading` — set the current font name (plus an accessibility description via `Phrase`), preview it in its own typeface, and disable the row while fonts load.
- `premiumUpsellVisible` / `premiumSettingsVisible` — swap between the upsell banner and the full Premium controls (hiding the free text-size row and its divider).

## Junior notes
- `Binder` is Pocket's UI pattern: instead of exposing child views, each composite view exposes a `bind()` helper with chainable setters — hosts call `view.bind().brightness(...).marginUpClick(...)`.
- The brightness listener is stored in a field and invoked from an internal pass-through `OnSeekBarChangeListener` — setting a new listener replaces the old one; `clear()` nulls it.
- `Phrase` (`com.squareup.phrase.Phrase`) is a string-template helper for `"%s"`-style resources with named placeholders; used here for accessibility descriptions.
