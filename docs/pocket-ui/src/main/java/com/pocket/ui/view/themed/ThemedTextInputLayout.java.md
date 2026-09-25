# pocket-ui/src/main/java/com/pocket/ui/view/themed/ThemedTextInputLayout.java
## What this is
Material's TextInputLayout (a stock text-field wrapper that adds floating labels, hints, and error text around an EditText) with Pocket light/dark theme support. For the user it is a normal labeled input; the only addition over stock is theme-state merging.

## How it fits
Form screens wrap a ThemedEditText in this container to get floating-label inputs that stay readable in both themes. Its only addition is merging the theme attributes from AppThemeUtil in `onCreateDrawableState()`, so state-list box, hint, and error colors re-resolve on theme switch.

## Key pieces
- `onCreateDrawableState()` — merges `AppThemeUtil.getState(this)` into the drawable state; the whole reason the class exists.

## Junior notes
- Pair it with ThemedEditText inside; a stock EditText child would not resolve the compat theme colors.
- The base class comes from Material Components, not the platform, so Material version bumps can change its styling behavior.
