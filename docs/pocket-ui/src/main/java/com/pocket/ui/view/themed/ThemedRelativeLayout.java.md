# pocket-ui/src/main/java/com/pocket/ui/view/themed/ThemedRelativeLayout.java
## What this is
A RelativeLayout (a stock container that positions children relative to each other) that follows Pocket's light/dark theme. It exists only for screens not yet migrated to ConstraintLayout; it adds nothing over stock except theme-state merging.

## How it fits
Legacy layouts that still use RelativeLayout use this as a drop-in themed replacement so they keep working in dark mode. Its only behavior is merging the theme attributes from AppThemeUtil in `onCreateDrawableState()`. The class javadoc explicitly steers new code to ThemedConstraintLayout instead.

## Key pieces
- `onCreateDrawableState()` — merges `AppThemeUtil.getState(this)` into the drawable state; the whole reason the class exists.

## Junior notes
- Do not use this in new layouts; use ThemedConstraintLayout (or the Kotlin ThemedConstraintLayout2).
- If you migrate a screen off this class, swap the XML tag and confirm constraints cover what the relative rules did.
