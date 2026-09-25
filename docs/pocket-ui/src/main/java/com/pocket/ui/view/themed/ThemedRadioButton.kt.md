# pocket-ui/src/main/java/com/pocket/ui/view/themed/ThemedRadioButton.kt
## What this is
A radio button (a stock circular single-choice option, usually in a group where only one can be on) that follows Pocket's light/dark theme. No visual or behavior change beyond theme awareness.

## How it fits
Single-choice settings and dialogs use this instead of the stock RadioButton so the button tint follows the theme via state-list colors. Its only addition is merging the theme attributes from AppThemeUtil in `onCreateDrawableState()`.

## Key pieces
- `onCreateDrawableState()` — merges `AppThemeUtil.getState(this)` into the drawable state; the whole reason the class exists.

## Junior notes
- The base-class chain is stock AppCompatRadioButton plus one theme-state override.
- Theme colors come from the button's tint/state-list resources, not from code in this class.
