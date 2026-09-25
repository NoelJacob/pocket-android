# pocket-ui/src/main/java/com/pocket/ui/util/EnabledUtil.java

## What this is
A one-method helper that enables or disables every child of a `ViewGroup` (a view that contains other views, like a layout) in a single call. Android has no built-in "disable this whole subtree" call, so without this each screen would loop children by hand.

## How it fits
Screens and custom containers call `setChildrenEnabled` when a section should become interactive or inert together, for example disabling a form while it loads or a row while an action runs. Disabled state then flows into each child's own state-list styling and alpha handling.

## Key pieces
- `setChildrenEnabled(parent, enabled, deep)`: walks the direct children, sets each one's enabled flag, and recurses into nested `ViewGroup`s when `deep` is true. The `deep` flag exists so callers can choose between just the top row of children and the entire subtree.

## Junior notes
- Disabling a parent `ViewGroup` itself does NOT automatically disable its children for drawing/state purposes in all cases, which is why this walks and sets each child explicitly.
- This sets the flag but does not change click listeners or visibility; a disabled view still occupies layout space and still receives no clicks, which is the standard Android enabled semantic.
