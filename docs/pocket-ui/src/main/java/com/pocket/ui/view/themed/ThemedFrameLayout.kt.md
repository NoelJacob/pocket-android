# pocket-ui/src/main/java/com/pocket/ui/view/themed/ThemedFrameLayout.kt
## What this is
A FrameLayout (a stock container that stacks children on top of each other) that follows Pocket's light/dark theme. No visual or behavior change beyond theme awareness.

## How it fits
Used as a themed stacking container or fragment host in XML layouts. It merges the theme attributes from AppThemeUtil in `onCreateDrawableState()` so state-list backgrounds inside re-resolve on theme switch.

## Key pieces
- `onCreateDrawableState()` — merges `AppThemeUtil.getState(this)` into the drawable state; the whole reason the class exists.

## Junior notes
- The base-class chain is stock FrameLayout plus one theme-state override.
- `@JvmOverloads` generates the constructors XML inflation needs.
