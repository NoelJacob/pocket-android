# pocket-ui/src/main/java/com/pocket/ui/view/checkable/CheckableImageView.java

## What this is
An image view (icon) that can be checked on and off. Tapping it toggles the checked state, which swaps its drawable state (`state_checked`) so tint or selector drawables change appearance, and swaps its accessibility description to the checked variant when checked. It saves and restores its checked state across screen rotations.

## How it fits
Used for standalone toggle icons (favorite stars, toggle buttons) inside rows, toolbars, and reader controls. It extends ThemedImageView (the Pocket image view that applies theme-aware tinting) and delegates state to CheckableHelper. Callers set `setChecked()`, listen with `setOnCheckedChangeListener()`, and style the on/off look with state-list drawables or the `checkedDrawableColor` attribute; adapters rebind it per item with `setChecked()`.

## Key pieces
- `mCheckable` (CheckableHelper): owns checked/checkable flags, the change listener, save/restore, and the checked content description; all public methods forward to it.
- `toggle()` / `setChecked()` / `setCheckable()` / `isChecked()` / `isCheckable()`: the Checkable contract; `toggle()` flips and notifies the listener.
- `performClick()`: toggles before dispatching click listeners and plays a click sound when nothing consumed the tap, so the icon always feels responsive.
- `onCreateDrawableState()`: merges `CHECKED_STATE_SET` / `CHECKABLE_STATE_SET` so state-list drawables and tints react.
- `drawableStateChanged()`: hook the helper uses (via the view callback) to refresh tint/content description when the drawable state changes.
- `onSaveInstanceState()` / `onRestoreInstanceState()`: persist the checked state through configuration changes via the helper's parcelable wrapper.
- `setContentDescription()`: routes through the helper so the `checkedContentDescription` XML value is announced when checked.
- `setOnCheckedChangeListener()`: registers the callback fired on every checked flip.

## Junior notes
- ThemedImageView (parent) adds theme-aware tinting on top of the stock ImageView; the checked state just feeds extra flags into that machinery via drawable state.
- Custom XML attributes come from `CheckableHelper` (`isCheckable`, `checkedContentDescription`), not from this class itself.
- Unlike CheckableTextView, this class has no databinding adapter, so layouts set its state from code or a custom binding adapter at the call site.
