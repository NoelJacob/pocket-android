# pocket-ui/src/main/java/com/pocket/ui/view/checkable/CheckableConstraintLayout.java

## What this is
A ConstraintLayout (a layout that positions children with relative constraints) that can be checked on and off, like a checkbox row. Tapping it toggles its checked state and updates its drawable state (the `state_checked` / `state_checkable` flags Android uses to pick selector backgrounds), so the whole row can visually show selected versus unselected. It supports an alternate accessibility description (TalkBack text) while checked.

## How it fits
Used as the root of tappable selectable rows (for example list rows that behave like toggles), wherever a whole container rather than a single widget needs checkable behavior. It extends VisualMarginConstraintLayout (a ConstraintLayout that accounts for font visual margins) and delegates all check logic to CheckableHelper. Parents such as RecyclerView adapters or databinding layouts call `setChecked()` / `toggle()`, and register a `setOnCheckedChangeListener()` to react; `shouldPropagateChecks()` controls whether the checked state is pushed down into child checkable views.

## Key pieces
- `mCheckable` (CheckableHelper): owns the actual checked/checkable flags, listeners, saved state, and the checked-versus-unchecked content description; every method on this class forwards to it, which is WHY the class is so thin.
- `setChecked()` / `toggle()` / `isChecked()` / `setCheckable()` / `isCheckable()`: the Checkable contract (Android's `android.widget.Checkable` interface: something with an on/off state); toggling fires the change listener.
- `performClick()`: toggles first, then runs the normal click listeners, and plays a click sound even when no listener consumed the tap, so the row always gives touch feedback.
- `onCreateDrawableState()`: merges `CHECKED_STATE_SET` / `CHECKABLE_STATE_SET` into the view's drawable state so selector drawables (backgrounds that change with state) react to checked.
- `setContentDescription()`: reroutes through the helper so the helper can swap in the `checkedContentDescription` text when checked.
- `init(attrs)`: reads the `CheckableHelper` XML attributes (`isCheckable`, `checkedContentDescription`) from the layout tag.
- `shouldPropagateChecks(Boolean)`: opt-in flag forwarded to the helper for propagating the checked state to checkable children.

## Junior notes
- CheckableHelper is the shared engine behind all three checkable views; learn it once and the ImageView/TextView variants work the same way.
- `performClick()` toggling BEFORE `super.performClick()` is deliberate: click listeners observe the new state, not the old one.
- Gotcha noted in CheckableHelper itself: checkable views default to clickable and swallow touches, so set `clickable="false"` explicitly if the row should not intercept taps.
