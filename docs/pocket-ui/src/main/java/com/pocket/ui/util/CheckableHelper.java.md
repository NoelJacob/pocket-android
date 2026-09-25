# pocket-ui/src/main/java/com/pocket/ui/util/CheckableHelper.java

## What this is
A composition helper that adds checkable (on/off, like a checkbox or toggle) behavior to any custom View. Android's `Checkable` interface (an object that can be checked, unchecked, and toggled) is painful to implement by hand: drawable states, listeners, rotation survival. This class holds all of that state and logic so the host view just forwards a few method calls to it.

## How it fits
A custom view (for example a button or row that can be selected) creates one `CheckableHelper` field and forwards `setChecked`, `toggle`, `performClick`, `onCreateDrawableState`, and save/restore calls to it. Downstream, it notifies an `OnCheckedChangeListener`, refreshes the view's drawable state (so state-list colors and drawables react to `state_checked`), pushes the checked state into child views, and swaps the content description for accessibility.

## Key pieces
- `CHECKED_STATE_SET` / `CHECKABLE_STATE_SET`: the drawable-state markers merged into `onCreateDrawableState` so selectors and tint lists can style the checked state. They exist so theming works without each view redeclaring state arrays.
- `setChecked(boolean)`: the core state change. Fires the listener (guarded by `mIsBroadcasting` so a listener calling `setChecked` back does not infinitely recurse), reapplies the content description, refreshes drawable state, and propagates to children.
- `setCheckable(boolean)` / `isCheckable()` / `toggle()`: a view can be checkable-capable but currently non-toggleable. `toggle()` is a no-op unless checkable, which stops accidental toggles on plain rows.
- `initAttributes(Context, AttributeSet)`: reads the `isCheckable` and `checkedContentDescription` XML attributes and defaults the view to clickable (checkable views need clicks to toggle). Removes per-view attribute-parsing boilerplate.
- `setContentDescriptions(regular, checked)` plus `SuperSetContentDescription`: swaps the TalkBack description when checked (for example "Saved" vs "Save"). The setter interface exists because the helper cannot call `super.setContentDescription` itself, so the host passes a lambda to it.
- `shouldPropagateChecks(Boolean)`: turns child propagation on/off. Needed when a container holds nested checkables that should (or should not) follow the parent.
- `onSaveInstanceState` / `onRestoreInstanceState` with inner `SavedState`: survives rotation. The host view wires these into its own saved-state methods.
- `Checkable` (inner interface extending `android.widget.Checkable`): the contract host views implement, adding `setCheckable` and the listener setter.
- `setChildrenChecked(View, boolean)`: recursively checks any child implementing `android.widget.Checkable`. This is why checking a row can check its inner toggle for free.

## Junior notes
- The host view MUST forward `onCreateDrawableState` and merge both state sets, otherwise selectors never see the checked state and nothing visually changes.
- `toggle()` is normally called from `performClick()` before `super.performClick()`, so click listeners observe the new state. Ordering matters here.
- This class holds a strong reference to the host `View`; always create it as an instance field of that view, never share it or hold it longer than the view lives.
