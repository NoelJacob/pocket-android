# pocket-ui/src/main/java/com/pocket/ui/view/menu/MenuItem.java

## What this is
A plain data object describing one row in a popup menu: a label (string resource), an icon (drawable resource), what happens on tap, and an optional analytics identifier. It also carries mutable visible/enabled flags that are re-evaluated each time the menu opens.

## How it fits
Created by feature screens (e.g. list-item overflow menus) and handed to `ThemedPopupMenu` inside a `Section` (actions or radio list). `ThemedPopupMenu` reads `label`/`icon` to render each row and calls `onClick(view)` when tapped; it checks `isVisible()`/`isEnabled()` right before display to skip or grey out rows.

## Key pieces
- `label` (`@StringRes int`) — row text; a resource id, not a literal string, so it localizes.
- `icon` (int) — drawable resource for action rows; radio rows ignore it.
- `onClick` (`View.OnClickListener`) — tap handler; `onClick(view)` safely no-ops when null.
- `uiEntityIdentifier` — optional analytics tag for the row.
- `groupId` — hardcoded to 1; reserved grouping field, currently unused for behavior.
- `setVisible` / `isVisible` — whether the row shows at all; queried fresh on every menu open.
- `setEnabled` / `isEnabled` — whether the row is tappable; a disabled row still shows but does not fire.

## Junior notes
- `@StringRes` is a Lint annotation — it does not convert anything, it just warns you at build time if you pass a non-string resource id.
- Visibility/enabled are plain booleans with setters, so the host must set them before calling `ThemedPopupMenu.show()` — there is no observable/automatic refresh.
