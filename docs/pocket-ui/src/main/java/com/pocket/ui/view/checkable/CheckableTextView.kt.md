# pocket-ui/src/main/java/com/pocket/ui/view/checkable/CheckableTextView.kt

## What this is
A text label that can be checked on and off, used for selectable option rows and toggle chips rendered as text. Tapping it flips its checked state, which feeds `state_checked` / `state_checkable` into its drawable state so text color or background selectors change, and plays a click sound when no click listener consumed the tap. Its checked state can be driven directly from XML layouts via databinding (XML layouts bound to ViewModel fields).

## How it fits
Used for checkable text options (settings rows, filter options) where the whole label is the toggle. It extends ThemedTextView (Pocket's theme-aware TextView) and delegates state to CheckableHelper. Layouts bind `app:checked="@{viewModel.selected}"` through the `checked` BindingAdapter (a static method that lets XML set a property); code can also call `setChecked()` / `toggle()` and observe `setOnCheckedChangeListener()`.

## Key pieces
- `checkableHelper` (CheckableHelper): owns the checked/checkable flags and listener; every Checkable method forwards to it, which is WHY this file has almost no logic of its own.
- `setChecked()` / `setCheckable()` / `isChecked()` / `isCheckable()` / `toggle()`: the Checkable contract implementation.
- `performClick()`: toggles, delegates to super, and plays the click sound effect when unhandled, so taps always give audio feedback.
- `onCreateDrawableState()`: merges `CHECKED_STATE_SET` when checked and `CHECKABLE_STATE_SET` when checkable, driving selector colors.
- `setOnCheckedChangeListener()`: forwards listener registration to the helper.
- `isChecked(view, isChecked)` companion BindingAdapter `"checked"`: lets databinding expressions set checked state from XML; `@JvmStatic` exposes the Kotlin companion method as a Java static so the databinding processor can find it.

## Junior notes
- Databinding adapters are static glue: `app:checked="@{...}"` in a layout XML calls `isChecked()` here. The attribute name (`"checked"`) is unrelated to the method name.
- Custom XML attributes (`isCheckable`, `checkedContentDescription`) are read via `checkableHelper.initAttributes(context, attrs)` in each constructor; constructors must all call it.
- `coroutines` (background tasks) and Hilt DI (constructor parameters provided automatically) are not involved here; state is plain synchronous view state.
