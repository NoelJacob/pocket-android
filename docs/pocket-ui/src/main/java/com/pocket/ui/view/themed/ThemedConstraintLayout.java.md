# pocket-ui/src/main/java/com/pocket/ui/view/themed/ThemedConstraintLayout.java
## What this is
A ConstraintLayout (a stock container that positions children with relative constraints) that follows Pocket's light/dark theme. It adds nothing visual; theme-aware state-list backgrounds and text colors inside it just update on theme switch.

## How it fits
This is the default themed container: FullscreenProgressView, SettingsImportantButton, and most pocket-ui composite views extend it, and screens use it directly in XML layouts. Its only behavior beyond stock is merging the theme attributes from AppThemeUtil into its drawable state in `onCreateDrawableState()`, which propagates the theme state to state-list drawables.

## Key pieces
- `onCreateDrawableState()` — merges `AppThemeUtil.getState(this)` into the drawable state; the whole reason the class exists.

## Junior notes
- Prefer this over ThemedRelativeLayout for new layouts; RelativeLayout is kept only for not-yet-migrated screens.
- The Java and Kotlin (`ThemedConstraintLayout2`) variants are interchangeable in behavior; check imports when both are on the classpath.
