# pocket-ui/src/main/res/drawable/bg_bottom_sheet.xml
## What this is
This file is the `bg_bottom_sheet` shape drawable (a rectangle/pill drawn in XML). It renders fill `@color/pkt_bg` behind chips, cards, snackbars, bottom sheets, skeleton placeholders, or badge pills, keeping fills and corner radii consistent instead of each layout inventing its own. 
## How it fits
It is set as `android:background` on the corresponding custom view or layout. Known references: layouts/drawables: `Pocket/src/main/res/layout/frag_sort_filter_bottom_sheet.xml`, `Pocket/src/main/res/layout/frag_tag_bottom_sheet.xml`, `Pocket/src/main/res/layout/fragment_font_settings_bottom_sheet.xml`, `Pocket/src/main/res/layout/fragment_highlights_bottom_sheet.xml`. Downstream it is pure visuals: it draws pixels behind content and produces no data.
Inventory:
- Fill: `@color/pkt_bg`.
