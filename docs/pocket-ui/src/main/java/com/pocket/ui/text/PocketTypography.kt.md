# pocket-ui/src/main/java/com/pocket/ui/text/PocketTypography.kt

## What this is

The Compose type scale for the app: a `PocketTypography` data class holding eleven `TextStyle` slots, `h1`–`h7` for headings (large sizes, medium weight) and `p1`–`p4` for body text (smaller sizes, normal weight), each with a font size and line height. It also provides the `Graphik()` function that builds a Compose `FontFamily` (a set of font files mapped to weights/styles) from the Graphik asset files, and a `LocalPocketTypography` composition local (a Compose-scoped value passed implicitly down the UI tree) carrying the current scale.

## How it fits

The app's Compose theme provides a `PocketTypography` (usually copied with `withDefaultFontFamily(Graphik(assets))` so every style uses Graphik) via `LocalPocketTypography`, and individual screens read e.g. `LocalPocketTypography.current.h4` for their `Text()` styles. The `Graphik(assets)` mapping reuses `Fonts.Font.*.filename` so the Compose font files stay in sync with the View-system font table. `withDefaultFontFamily` applies the family to all eleven styles at once instead of setting each by hand.

## Key pieces

- `PocketTypography(h1..h7, p1..p4)` — WHY: named slots keep heading/body sizing consistent across screens; defaults encode the design spec (e.g. h1 48sp/60lh medium, p4 14sp/20lh normal).
- `withDefaultFontFamily(fontFamily)` — WHY: `copy()`s the whole scale with one `FontFamily` applied to every style; avoids eleven repetitive assignments at each theme site.
- `LocalPocketTypography = staticCompositionLocalOf { PocketTypography() }` — WHY: the implicit channel themes use to hand the scale to every composable below them without threading a parameter through each function.
- `Graphik(assets: AssetManager)` — WHY: builds the Compose `FontFamily` from the four Graphik asset files (regular + italic at normal weight, medium + italic at medium weight, bold), wiring each file to its `FontWeight`/`FontStyle` so Compose auto-picks the right file for bold/italic text.

## Junior notes

- `staticCompositionLocalOf` never changes its instance identity; updating the provided value triggers recomposition of readers, so provide it once high in the tree (at the theme level).
- `sp` means scale-independent pixels: the size honors the user's system font-size setting, which is why the icon-font trick in `IconFont` scales along with text.
