# pocket-ui/src/main/java/com/pocket/ui/view/themed/Themed.kt
## What this is
The core contract of Pocket's View theming system: anything that knows the current light/dark theme implements this interface. It has two halves: `getThemeState()` exposes the theme as drawable-state attributes (so state-list colors auto-update), and `getThemeColors()` plus `getThemeColorsChanges()` expose it as data (an RxJava Observable stream, meaning subscribers get pushed each theme change) for code that needs the value directly.

## How it fits
This is the foundation every Themed* view builds on. The Application, Activity, or a parent view implements Themed; AppThemeUtil walks up from any view to find the nearest one. Themed views merge `getThemeState()` into their drawable state, so switching dark mode re-resolves colors without recreating anything. Compose screens use the `ThemeColors` LIGHT/DARK value through PocketTheme instead.

## Key pieces
- `getThemeState(view)` — returns the theme as an IntArray of custom attributes (e.g. state_dark); merged into drawable state by every Themed view.
- `getThemeColors(context)` — current LIGHT/DARK value for imperative code.
- `getThemeColorsChanges(context)` — Observable stream of theme changes; Compose's PocketTheme subscribes to re-render on switch.
- `ThemeColors` — the LIGHT / DARK enum shared by the View and Compose systems.

## Junior notes
- Theme is inherited: AppThemeUtil checks the view, then its parents, then the Activity/Application context, so most screens theme automatically.
- Drawable-state theming is why theme switches are cheap: state lists just re-resolve, like pressed-state colors do.
