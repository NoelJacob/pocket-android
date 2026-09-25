# pocket-ui/src/main/java/com/pocket/ui/view/themed/ThemedSeekBar.java
## What this is
A SeekBar (a stock slider with a draggable thumb) that follows Pocket's light/dark theme. No slider behavior changes; theme-aware track and thumb colors just update on theme switch.

## How it fits
PocketSeekBar extends this class to build the custom-styled settings slider; the theme-state merging here is what lets its custom drawables re-resolve colors. Its only addition over AppCompatSeekBar is merging the theme attributes from AppThemeUtil in `onCreateDrawableState()`.

## Key pieces
- `onCreateDrawableState()` — merges `AppThemeUtil.getState(this)` into the drawable state; the whole reason the class exists.

## Junior notes
- Custom thumb/progress drawables must be stateful (like PocketSeekBar's ThemeDrawable) to actually receive the merged theme state.
- All three standard View constructors are present so it works from code and XML inflation.
