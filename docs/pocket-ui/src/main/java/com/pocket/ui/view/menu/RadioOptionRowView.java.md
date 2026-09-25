# pocket-ui/src/main/java/com/pocket/ui/view/menu/RadioOptionRowView.java

## What this is
One selectable row in a single-choice (radio) list: a text label in a 54dp-tall, full-width row with the standard Pocket touch highlight. Its checked state is rendered by the row background/selector (via the checkable base class), not by an embedded dot. Tapping it selects that option.

## How it fits
Inflated from `R.layout.view_radio_option_row_view` and used by `ThemedPopupMenu`'s `RadioOptionHolder` for `MenuType.RADIO` sections — the holder sets the label from a `MenuItem`, marks the preselected row checked, and dismisses + fires the item's click on tap. It extends `CheckableConstraintLayout`, so it plugs into Android's checked-state machinery (and `CheckableHelper` listeners) like the other rows.

## Key pieces
- `RadioOptionRowView` — extends `CheckableConstraintLayout`; `init()` inflates the layout, grabs the label, applies the touchable background, and blocks descendant focus so the row is one tappable unit.
- `sizeHelper` (`IntrinsicSizeHelper`) — enforces full-width × 54dp in `onMeasure` for uniform rows.
- `setLabel(@StringRes)` / `setLabel(CharSequence)` — set the row text from a resource or a literal.

## Junior notes
- Unlike `OptionRowView`, this row has no icon slot — label only. Pick this class for single-choice lists, `OptionRowView` for action menus.
- The checked visuals come from the background drawable's `state_checked` selector — if selection highlighting breaks, look at `cl_pkt_touchable_area` and the base class's `setChecked`, not this file.
