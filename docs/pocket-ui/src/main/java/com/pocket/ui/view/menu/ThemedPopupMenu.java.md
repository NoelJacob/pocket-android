# pocket-ui/src/main/java/com/pocket/ui/view/menu/ThemedPopupMenu.java

## What this is
A small themed dropdown menu anchored to a view: optional section titles plus rows that are either actions (icon + label) or a single-choice radio list with one preselected row. Tapping any row dismisses the popup and fires that row's handler. It is a plain helper object, not a View subclass.

## How it fits
Built by feature screens (e.g. overflow or sort menus) as `new ThemedPopupMenu(context, Section.actions(...), Section.radio(...))` and shown with `show(anchorView)`, which drops a `PopupWindow` below the anchor. Each `Section` wraps `MenuItem`s; on tap, the holder dismisses the window first, then calls `MenuItem.onClick`. Headers render as `SectionHeaderView`, radio rows as `RadioOptionRowView`, action rows as `OptionRowView`.

## Key pieces
- `ThemedPopupMenu(context, sections...)` — flattens sections into a `rows` list (header labels + items), records each item's `MenuType`, and pre-marks the radio section's selected item; builds the internal `RecyclerView` with popup background and 200dp minimum width.
- `show(anchor)` — dismisses any existing window, wraps the list in a focusable `PopupWindow`, and shows it as a dropdown offset from the anchor.
- `dismiss()` — closes the current window; also called automatically before showing and on every row tap.
- `HeaderHolder` — renders a `CharSequence` label via `SectionHeaderView` with the bottom divider off.
- `RadioOptionHolder.bind(option, selected)` — sets the label, checked state, and enabled flag; tap dismisses + fires the item.
- `ActionOptionHolder.bind(option)` — sets label and icon plus enabled flag; tap dismisses + fires the item.
- `MenuType` — ACTIONS vs RADIO, deciding which row layout each section uses.
- `Section` — factory-built group: `actions(label, options)` or `radio(label, selectedPosition, options)`; the label may be null for an untitled group.

## Junior notes
- `MenuItem.isVisible()` is never consulted here — every item passed in is rendered; filter hidden items yourself before building sections.
- `showAsDropDown(anchor, -width, -height)` positions the popup relative to the anchor — the negative offsets shift it up/left, so moving the anchor moves the menu; test with RTL layouts and near screen edges.
