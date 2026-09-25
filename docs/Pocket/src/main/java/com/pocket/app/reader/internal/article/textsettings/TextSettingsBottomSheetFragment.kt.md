# Pocket/src/main/java/com/pocket/app/reader/internal/article/textsettings/TextSettingsBottomSheetFragment.kt
## What this is
The user-visible "Aa" display-settings bottom sheet in the reader: font size, line height, margin steppers, brightness slider, light/dark/system theme toggle, current-font row, and the Premium upsell. It renders `TextSettingsBottomSheetViewModel` state and forwards every tap to it.
## How it fits
Opened from the reader toolbar/article overflow. Hilt DI (constructor params provided automatically) injects `DisplaySettingsManager`, `SystemDarkTheme`, `Theme`, and `Premium`; the layout is databound to the ViewModel so stepper enable-states and premium rows update automatically. One-shot `Event`s drive navigation: `ShowFontChangeBottomSheet` swaps this sheet for `FontSettingsBottomSheetFragment`, `ShowPremiumScreen` opens the paywall via `Premium.showPremiumForUserState` — both dismiss this sheet first.
## Key pieces
- `setupView()` — wires the `DisplaySettingsView` builder: brightness slider (forwards to `onBrightnessChanged` plus applies it live via `Brightness.applyBrightnessIfSet`), the six stepper callbacks, the premium-upgrade and font-change rows, and the theme toggle with its listener plus initial `ThemeChoice` derived from system/app theme.
- `setupEventListener()` — collects ViewModel events with `collectWhenResumed` (a helper that collects a flow only while the fragment is resumed) and performs the fragment swaps / paywall launches.
- `onViewCreated` ordering — event listener, then view wiring, then `viewModel.onInitialized()` so initial state lands on an already-bound view.
## Junior notes
- `ViewUtil.refreshDrawableStateDeep(binding.root)` after a theme pick forces the already-open sheet to redraw in the new theme — without it the sheet would keep the old colors until reopened.
- `_binding = null` in `onDestroyView` is mandatory with view binding in fragments: the fragment outlives its view, and holding the binding leaks the whole view hierarchy.
