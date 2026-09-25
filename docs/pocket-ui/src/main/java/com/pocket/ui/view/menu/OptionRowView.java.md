# pocket-ui/src/main/java/com/pocket/ui/view/menu/OptionRowView.java

## What this is
One tappable action row in a menu: an optional leading icon plus a text label, 54dp tall and full-width. It is checkable-capable (inherits the checked-state machinery) and shows the standard Pocket touch highlight. When no icon is set, the icon slot collapses so the label aligns cleanly.

## How it fits
Inflated from `R.layout.view_option_row_view` and used wherever menus list actions — notably by `ThemedPopupMenu`'s `ActionOptionHolder`, which sets the label/icon from a `MenuItem` and forwards taps to it. Hosts call `setLabel`/`setIcon` directly; checked state comes from the `CheckableConstraintLayout` base class.

## Key pieces
- `OptionRowView` — extends `CheckableConstraintLayout` (a theme-aware ConstraintLayout that also implements Android's `Checkable` contract); `init()` inflates the layout, sets the touchable background, and blocks focus from going to children so the whole row acts as one tappable unit.
- `sizeHelper` (`IntrinsicSizeHelper`) — forces full-width × 54dp in `onMeasure`, so rows are uniform regardless of parent.
- `setLabel(@StringRes)` — sets text via `setTextAndUpdateEnUsLabel`, which also refreshes a debug English label (used by Pocket's translation QA).
- `setIcon(Drawable)` / `setIcon(@DrawableRes)` — sets the icon and hides the `ImageView` (GONE) when there is no drawable, so layout collapses the gap.

## Junior notes
- `FOCUS_BLOCK_DESCENDANTS` means key/D-pad focus lands on the row itself, never on inner views — important for TV/keyboard navigation.
- `CheckableConstraintLayout` gives this row a checked state (used with different backgrounds/selectors) even though an action row rarely shows it — the sibling `RadioOptionRowView` is the one that visibly uses it.
