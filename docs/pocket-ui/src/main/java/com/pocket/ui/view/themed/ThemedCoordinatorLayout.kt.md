# pocket-ui/src/main/java/com/pocket/ui/view/themed/ThemedCoordinatorLayout.kt
## What this is
A CoordinatorLayout (a stock super-powered FrameLayout that coordinates animations between children, e.g. scrolling toolbars) that follows Pocket's light/dark theme. No behavior changes beyond theme awareness.

## How it fits
Screens whose root needs coordinator behaviors (app bars that collapse on scroll, floating buttons that dodge snackbars) use this as the themed root instead of the stock CoordinatorLayout. It merges the theme attributes from AppThemeUtil in `onCreateDrawableState()` so state-list backgrounds inside re-resolve on theme switch.

## Key pieces
- `onCreateDrawableState()` — merges `AppThemeUtil.getState(this)` into the drawable state; the whole reason the class exists.

## Junior notes
- The base-class chain is stock CoordinatorLayout plus one theme-state override; all coordinator behaviors are untouched.
- `@JvmOverloads` generates the constructors XML inflation needs.
