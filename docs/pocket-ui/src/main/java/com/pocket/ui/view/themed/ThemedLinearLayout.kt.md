# pocket-ui/src/main/java/com/pocket/ui/view/themed/ThemedLinearLayout.kt
## What this is
A LinearLayout (a stock container that arranges children in a single row or column) that follows Pocket's light/dark theme. No visual or behavior change beyond theme awareness.

## How it fits
SkeletonParagraphView extends it, and XML layouts use it for themed horizontal/vertical stacks. It merges the theme attributes from AppThemeUtil in `onCreateDrawableState()` so state-list backgrounds inside re-resolve on theme switch.

## Key pieces
- `onCreateDrawableState()` — merges `AppThemeUtil.getState(this)` into the drawable state; the whole reason the class exists.

## Junior notes
- The base-class chain is stock LinearLayout plus one theme-state override.
- `@JvmOverloads` generates the constructors XML inflation needs.
