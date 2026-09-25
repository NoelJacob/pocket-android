# pocket-ui/src/main/res/values/styles.xml
## What this is
This file is the pocket-ui widget style library: named `style` bundles (a reusable set of view properties applied with `style="@style/..."`, like CSS classes) for icon buttons, text appearances, bottom sheets, snackbars, dialogs, and drawer rows. The `Pkt_IconButton` family (base plus `_Archive`, `_Share`, `_Close`, `_Overflow`, `_Listen`, coral/amber checked variants, and more) gives every toolbar the same 50dp touch target, borderless ripple, tint, and content description in one line. 
## How it fits
Layouts and custom views apply these via `style="@style/<name>"`, and `values/themes.xml` wires the defaults (`iconButtonStyle`, `simpleDrawerRowStyle`, `subheaderStyle`) so themed views pick them up automatically. Upstream app screens (reader toolbar, My List rows, dialogs, settings) get consistent buttons and text; downstream a style change (touch size, ripple, tint) propagates everywhere that style is used.
Inventory:
- 65 styles.
- `Pkt_BottomDrawerDialog*` (1): `Pkt_BottomDrawerDialog`
- `Pkt_EditTextAppearance*` (1): `Pkt_EditTextAppearance`
- `Pkt_IconButton*` (33): `Pkt_IconButton`, `Pkt_IconButton_CoralChecked`, `Pkt_IconButton_AmberChecked`, `Pkt_IconButton_Overflow`, `Pkt_IconButton_Up`, `Pkt_IconButton_Previous`, `Pkt_IconButton_Next`, `Pkt_IconButton_X` ...
- `Pkt_SkeletonImage*` (1): `Pkt_SkeletonImage`
- `Pkt_Text*` (21): `Pkt_Text_H5`, `Pkt_Text_P3`, `Pkt_Text_P4`, `Pkt_Text_Extra_Large_Title`, `Pkt_Text_Large_Title`, `Pkt_Text_Title`, `Pkt_Text_LightTitle`, `Pkt_Text_Medium` ...
- `Pkt_TextFloatingLabelAppearance*` (1): `Pkt_TextFloatingLabelAppearance`
- `Pkt_ThickDivider*` (1): `Pkt_ThickDivider`
- `Pkt_ThinDivider*` (1): `Pkt_ThinDivider`
- `SimpleDrawerRow*` (1): `SimpleDrawerRow`
- `Subheader*` (1): `Subheader`
- `TitleRowStyle*` (1): `TitleRowStyle`
- `TransparentBottomSheetDialogTheme*` (1): `TransparentBottomSheetDialogTheme`
- `TransparentBottomSheetStyle*` (1): `TransparentBottomSheetStyle`
