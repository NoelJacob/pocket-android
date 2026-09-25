# Pocket/src/main/java/com/pocket/util/android/view/ViewEnabledManager.java
## What this is
A small AND-gate for a view's enabled state (enabled = tappable and normally colored; disabled = greyed out and ignoring taps). Register any number of `ViewEnabledCondition`s; the view is enabled only while every condition returns true. Whenever a condition's answer may have changed, call `invalidate()`.
## How it fits
Used by `ItemsTaggingFragment` for the save button (in words: `new ViewEnabledManager(saveButton)`, add conditions, call `invalidate()` on text/selection changes) and by `TagModuleManager` via the `ViewEnabledCondition` interface. It consumes condition callbacks and produces `view.setEnabled(...)` calls.
## Key pieces
- `ViewEnabledManager(view)` — WHY: binds the manager to exactly one view. Usage in words: create it right after finding the button.
- `addCondition(enabler)` — WHY: adds one vote to the AND-gate and immediately refreshes. Usage in words: add a condition per requirement (e.g. non-empty text, valid selection).
- `invalidate()` — WHY: re-evaluates all conditions and pushes the result to the view. Usage in words: call from every listener that could flip a condition.
- `ViewEnabledCondition.isEnabled()` — WHY: the pluggable vote; implement per rule.
## Junior notes
- Empty condition list means enabled; there is no "default disabled" state, so add conditions before showing the UI if it should start disabled.
- `invalidate()` here means "recompute enabled state", not Android's `View.invalidate()` (redraw); the name overlap is confusing but the call targets are different objects.
- Conditions are only polled on `addCondition`/`invalidate`; there is no automatic observation, so forgetting to call `invalidate()` leaves stale state.

