# pocket-ui/src/main/java/com/pocket/ui/view/item/SaveButton.java
## What this is
The "Save / Saved" toggle button: an icon plus a text label that flips between "Save" and "Saved" when tapped. The user sees it on item cards, article headers, and share sheets wherever a one-tap save/unsave action is offered. It is checkable (`checked` = saved), with a borderless ripple background for touch feedback.
## How it fits
Dropped into any layout needing a save toggle (reader header, item details, recommendations). Hosts call `bind().setSaved(isSaved)` to reflect the item's state and `bind().setOnSaveButtonClickListener(...)` to persist the change; `bind().label(visible)` hides the text to leave an icon-only button. It extends `CheckableConstraintLayout` and inflates `R.layout.view_save` (`save_icon`, `save_label`).
## Key pieces
- `Binder.setSaved(boolean)` — the state setter: detaches the click listener, sets checked, reattaches, refreshes the label. WHY: detaching during programmatic sets prevents a mere rebind from firing the save callback and double-saving.
- `Binder.setOnSaveButtonClickListener(OnSaveButtonClickListener)` — installs the host callback; defaults to a no-op that accepts the toggle. WHY: the button works (visually toggles) even with no listener, so previews and simple screens need no wiring.
- `OnSaveButtonClickListener.onSaveButtonClicked(view, saved)` — returns the state the button should actually end in. WHY: lets the host veto the toggle (e.g. login required, save failed) — if the return differs from the requested state, the button snaps back.
- `checkedListener` (internal) — compares requested vs. confirmed state and reverts plus skips the label update on veto. WHY: single choke point where the veto rule is enforced.
- `updateSaveLabel()` — swaps the label between `R.string.ic_save` / `R.string.ic_saved` and mirrors it to the accessibility content description. WHY: keeps the visible text and screen-reader announcement in sync.
## Junior notes
- Never call `setChecked()` directly from outside — it bypasses the veto logic. Always go through `bind().setSaved()` for programmatic changes so the listener detachment protects you.
- The save icon has `setLongClickable(false)` because icon buttons normally show a tooltip on long-press; here a tooltip would conflict with the row's own long-press (multi-select) behavior.
