# pocket-ui/src/main/java/com/pocket/ui/view/themed/PocketColors.kt
## What this is
The Compose color palette: one immutable data class holding every themed color (background, seven greys, two teals, plus derived onTeal/onBackground/cardBackground), with two fixed instances, LightColors and DarkColors. Compose code reads the current set from PocketTheme.colors instead of hardcoding hex values.

## How it fits
PocketTheme picks LightColors or DarkColors based on the current ThemeColors and publishes the choice through LocalPocketColors (a CompositionLocal, meaning an implicit value passed down the UI tree without threading parameters). Skeletons.kt and other Compose UI read `PocketTheme.colors.grey6` and similar. The View system uses separate color-state-list resources; this file is Compose-only.

## Key pieces
- `PocketColors` — the palette data class; `onBackground` defaults to grey1 and `cardBackground` to background so themes only override what differs.
- `LightColors` / `DarkColors` — the two concrete palettes; note greys invert between them while teals stay constant.
- `LocalPocketColors` — the CompositionLocal carrying the active palette, defaulting to LightColors.

## Junior notes
- `@Immutable` tells Compose the palette never changes in place, so reads of it skip needless recomposition; theme switches swap the whole object.
- Default parameter values (`onBackground = grey1`) keep the dark/light definitions short and prevent the two from drifting apart.
