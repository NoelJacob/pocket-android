# pocket-ui/src/main/res/drawable/bg_button.xml
## What this is
This file is the `bg_button` shape drawable (a rectangle/pill drawn in XML). It renders fill `@color/pkt_themed_teal_2`, corner radius `4dp` behind chips, cards, snackbars, bottom sheets, skeleton placeholders, or badge pills, keeping fills and corner radii consistent instead of each layout inventing its own. 
## How it fits
It is set as `android:background` on the corresponding custom view or layout. Downstream it is pure visuals: it draws pixels behind content and produces no data.
Inventory:
- Fill: `@color/pkt_themed_teal_2`.
- Corners: `4dp`.
