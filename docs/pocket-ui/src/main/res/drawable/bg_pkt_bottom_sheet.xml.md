# pocket-ui/src/main/res/drawable/bg_pkt_bottom_sheet.xml
## What this is
This file is the `bg_pkt_bottom_sheet` shape drawable (a rectangle/pill drawn in XML). It renders fill `@color/pkt_bg`, corner radius `@dimen/pkt_card_corner_radius` behind chips, cards, snackbars, bottom sheets, skeleton placeholders, or badge pills, keeping fills and corner radii consistent instead of each layout inventing its own. 
## How it fits
It is set as `android:background` on the corresponding custom view or layout. Downstream it is pure visuals: it draws pixels behind content and produces no data.
Inventory:
- Fill: `@color/pkt_bg`.
- Corners: `@dimen/pkt_card_corner_radius`.
