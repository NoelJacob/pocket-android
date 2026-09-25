# pocket-ui/src/main/java/com/pocket/ui/view/menu/ThemeToggle.java

## What this is
A three-way app-theme picker showing Light, Dark, and Auto swatches side by side. The user sees small theme previews; the currently active one gets a teal ring drawn around it. Tapping a swatch selects it and notifies the host.

## How it fits
Inflated from `R.layout.view_theme_toggle` and embedded in `DisplaySettingsView` (the reader settings panel), which exposes it via `Binder.theme()`. The reader screen sets the current theme with `theme(choice)`, restricts visible options with `availableThemes(...)` (e.g. hiding Auto), and receives taps through `OnThemeSelectedListener`.

## Key pieces
- `ThemeChoice` — enum (LIGHT, DARK, AUTO) mapping each choice to its swatch view id (`R.id.theme_light` etc.).
- `init()` — inflates the layout, creates the ring drawable, caches the three swatch views, and wires each tap to fire the listener then move the ring.
- `drawChild(...)` — after drawing the selected swatch, draws the `ThemeToggleSelectionDrawable` ring exactly over that child's bounds, inheriting its drawable state for theming.
- `setCurrentSelection(view)` — moves the ring and invalidates so `drawChild` repaints (internal; hosts use `Binder.theme()`).
- `Binder.clear()` — no listener, all themes visible, selection reset to the first swatch.
- `Binder.availableThemes(...)` — hides every swatch, then shows only the listed ones (used to hide Auto where unsupported).
- `Binder.theme(value)` — moves the ring to the given choice; null leaves the ring where it is.
- `Binder.listener(...)` — tap callback receiving the tapped view and its `ThemeChoice`.
- `OnThemeSelectedListener` — single-method interface for theme taps.

## Junior notes
- The ring is painted in `drawChild`, not as a view background — so it always tracks the selected swatch even as layout shifts; `invalidate()` after changing selection is what triggers the repaint.
- Selection here is purely visual: tapping fires the listener, but the host must actually apply the app theme — this view never changes the theme itself.
