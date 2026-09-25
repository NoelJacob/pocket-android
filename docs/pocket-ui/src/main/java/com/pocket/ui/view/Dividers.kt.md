# pocket-ui/src/main/java/com/pocket/ui/view/Dividers.kt

## What this is
The Compose (modern declarative UI toolkit) divider: `ThinDivider()` draws a full-width, one-hairline-tall horizontal rule in the theme's `grey6` color. In lists and under headers it is the faint line that visually separates rows or sections. The height comes from the `pkt_thin_divider_height` dimension resource so every divider in the app is exactly the same thickness.

## How it fits
Compose screens drop `ThinDivider()` at the bottom of a row or between list items; the Compose `AppBar.kt` in the same package calls it unconditionally as the last element of its column so every app bar gets its underline. The commented-out lines in `DividersPreview` (`ThickDivider()`, etc.) show thicker variants were planned but never added — this file currently ships only the thin one. The legacy View system equivalent is the `@style/Pkt_ThinDivider` divider view inside `view_app_bar.xml`, not this file.

## Key pieces
- `ThinDivider(modifier)`: fills max width, sets height from `pkt_thin_divider_height`, paints `PocketTheme.colors.grey6` — WHY a shared function instead of inline `Box` code is so color (theme-aware grey) and height stay consistent everywhere.
- `DividersPreview`: a `@Preview` (renders in Android Studio without running the app) showing the divider inside a padded column so designers can eyeball it.

## Junior notes
- Color comes from `PocketTheme.colors`, not a hardcoded value — the same code draws correctly in light and dark mode.
- `modifier: Modifier = Modifier` default parameter is the Compose convention letting callers add padding or click handling without the function needing overloads.
