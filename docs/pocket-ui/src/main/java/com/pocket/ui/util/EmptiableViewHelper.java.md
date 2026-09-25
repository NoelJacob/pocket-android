# pocket-ui/src/main/java/com/pocket/ui/util/EmptiableViewHelper.java

## What this is
The ready-made implementation of `EmptiableView` (a view that can announce it has no content). Custom views keep one of these as a field instead of hand-rolling listener storage and change detection. It also bundles a static helper for checking whether a container has any visible children left.

## How it fits
A view implementing `EmptiableView` constructs this helper with itself and a default listener (usually `GONE_WHEN_EMPTY`), forwards `setOnEmptyChangedListener` to it, and calls `setEmpty(true/false)` whenever its bound data changes. Parent layouts then collapse or reflow automatically, and container views use `hasVisibleChildren` to decide if a whole section should hide.

## Key pieces
- Constructor `(view, defaultListener)`: captures the view that change events report about and the initial policy. Passing the default up front means most views need no extra setup.
- `setEmpty(boolean)`: fires the listener only when the value actually changes. The change check exists to avoid redundant visibility churn and layout passes when data is rebound with the same emptiness.
- `setOnEmptyChangedListener(listener)`: swaps the policy at runtime so a parent can override the view's default behavior.
- `hasVisibleChildren(ViewGroup)`: scans children for any `VISIBLE` one. Lets section containers (rows, cards) treat "all children gone" as "I am empty too".

## Junior notes
- Call `setEmpty` every time bound data changes, including the transition back to non-empty. Forgetting the `false` call leaves the view stuck at `GONE`.
- `setEmpty` only notifies on change, so registering a new listener does not replay the current state. If a parent attaches late, have the view re-push its current emptiness after setting the listener.
