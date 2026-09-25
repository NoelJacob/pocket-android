# pocket-ui/src/main/java/com/pocket/ui/view/themed/ThemedConstraintLayout2.kt
## What this is
A ConstraintLayout (a stock container that positions children with relative constraints) that follows Pocket's light/dark theme. It is the Kotlin twin of ThemedConstraintLayout with identical behavior: stock layout plus theme-state merging.

## How it fits
Kotlin code and newer views (notably ThemedSwipeConstraintLayout, which extends this class) use this variant so they stay in Kotlin without crossing languages. Like its Java sibling it merges the theme attributes from AppThemeUtil in `onCreateDrawableState()`; screens can use either in XML layouts.

## Key pieces
- `onCreateDrawableState()` — merges `AppThemeUtil.getState(this)` into the drawable state; the whole reason the class exists.

## Junior notes
- Behaviorally identical to the Java ThemedConstraintLayout; the split is a Java/Kotlin interop convenience, not a feature difference.
- `@JvmOverloads` generates the one-, two-, and three-argument constructors Java and XML inflation expect from a single Kotlin constructor.
