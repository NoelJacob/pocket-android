# pocket-ui/src/main/java/com/pocket/ui/view/themed/ThemedView.java
## What this is
A plain View (Android's base UI building block: a rectangle that draws something and handles touches) with Pocket light/dark theme support. No drawing of its own; subclasses like SkeletonView add the visuals.

## How it fits
Used as the base for simple themed drawing views (SkeletonView extends it) and anywhere a lightweight themed spacer or canvas is needed. Its only addition over stock is merging the theme attributes from AppThemeUtil in `onCreateDrawableState()`.

## Key pieces
- `onCreateDrawableState()` — merges `AppThemeUtil.getState(this)` into the drawable state; the whole reason the class exists.

## Junior notes
- If you need a container, use one of the Themed layout classes instead; this is for leaf views with custom drawing.
- All three standard View constructors are present so it works from code and XML inflation.
