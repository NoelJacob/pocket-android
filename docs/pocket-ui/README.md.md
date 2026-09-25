# pocket-ui/README.md

## What this is

The style guide and catalog for the `pocket-ui` module, Pocket's shared design-system library of reusable buttons, badges, item rows, bottom sheets, snackbars, and typography. It documents naming conventions (`pkt_` prefixes), app-bar layout, the six box-button styles, icon-button checkable patterns, nested color-state lists, fonts, and bottom-sheet construction, then lists every component the module offers.

## How it fits

Feature code in `Pocket` (`com.pocket.app` screens) builds its UI out of these `com.pocket.ui.view.*` blocks instead of raw widgets, so a rebrand touches `pocket-ui` resources once. It names its own consumers explicitly: `NestedColorStateList` for `pkt_nst_` colors, `ThemedImageView`/`ThemedTextView` attrs, `Fonts.get()` for Graphik in code, `PktSnackbar` builder for snackbars, `ItemRowView` for items, and `BadgeView`/`BadgeLayout` for badges.

## Key pieces

- **Naming conventions (`pkt_`, `pkt_nst_`, `ic_pkt_`, `cl_pkt_`)** — why they exist: prefixes keep Pocket resources collision-free and the `nst_` marker warns that a color must load through `NestedColorStateList`, since stock Android silently flattens nested `ColorStateList` references to the default color.
- **Nested State Lists section** — why it exists: documents the workaround (load via `NestedColorStateList.get()`, or `app:drawableColor`/`app:compatTextColor` in layouts) that makes state selectors reusable; loading an `nst_` color normally won't crash, just renders wrong.
- **App Bar / Box Buttons / Icon Buttons / Dividers recipes** — why they exist: copy-paste XML (dimensions, `IconButton` styles with `srcCompat` + tooltip, `ThinDivider`/`ThickDivider`) so every screen's chrome matches without design review.
- **Fonts section (Graphik + WebView note)** — why it exists: points XML users at `app:typeface`, code at `Fonts.get(Context, Font)`, and reader-WebView work at the `Fonts` class docs, where the asset-font loading used by article templates is explained.
- **Bottom Sheets / Snackbar / Items / Badges sections** — why they exist: construction checklists (`CoordinatorLayout` + `BottomSheetBehavior` + `bg_pkt_bottom_sheet` + `BottomSheetDragHandle`; `SnackbarMessage` builder; `ItemRowView`; `BadgeView`/`BadgeLayout`) plus the WIP component catalog at the bottom.

## Junior notes

- **Styles carry the icon semantics.** An `IconButton` is just a frame; the `style="@style/ArchiveButton"` supplies the drawable, content description, and tooltip — always reuse or clone a style, never set `srcCompat` ad hoc.
- **`checkable` is opt-in on IconButtons.** A bookmark/archive toggle needs `<item name="checkable">true</item>` in its style or checked-state drawables never apply.
