# pocket-ui/src/main/java/com/pocket/ui/view/themed/PocketTheme.kt
## What this is
The Compose entry point for Pocket theming: a wrapper function that selects the light or dark palette and typography, then publishes them to everything inside it. Any @Composable (a UI function in Jetpack Compose, Android's declarative UI toolkit) placed inside `PocketTheme { ... }` automatically uses the right colors and fonts for the current theme.

## How it fits
Compose screens wrap their content in `PocketTheme`, mirroring what Themed* views do for XML layouts. It resolves the current ThemeColors via `themeColors()` (subscribing to the View system's theme stream so both UI systems stay in sync, falling back to the system dark-theme setting), maps it to LightColors/DarkColors, and feeds a matching MaterialTheme color scheme plus LocalPocketColors, LocalPocketTypography, content color, and default text style. `PocketTheme.colors`, `.typography`, and `.dimensions` are the read accessors used by Compose UI code.

## Key pieces
- `PocketTheme(content)` — picks the palette, builds Graphik-based typography, configures MaterialTheme (teal primary, themed background/surface), and provides the CompositionLocals.
- `PocketTheme` object — static accessors for colors, typography, and dimensions from any @Composable.
- `themeColors(context)` — finds the nearest Themed provider via AppThemeUtil and subscribes (RxJava subscribeAsState) to its changes; no provider means follow the system dark-theme setting.

## Junior notes
- This bridges two theming worlds: the View system's Themed provider remains the source of truth, and Compose just observes it.
- The MaterialTheme copy maps Pocket colors onto Material slots (primary, background, surface) so stock Material components roughly match Pocket styling.
