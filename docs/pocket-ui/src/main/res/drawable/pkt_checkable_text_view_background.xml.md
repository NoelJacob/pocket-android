# pocket-ui/src/main/res/drawable/pkt_checkable_text_view_background.xml
## What this is
This file is the `pkt_checkable_text_view_background` selector drawable (swaps child drawables by state). It renders container styling (padding, stroke, or state rows; see inventory) behind chips, cards, snackbars, bottom sheets, skeleton placeholders, or badge pills, keeping fills and corner radii consistent instead of each layout inventing its own. 
## How it fits
It is set as `android:background` on the corresponding custom view or layout. Downstream it is pure visuals: it draws pixels behind content and produces no data.
Inventory:
- See XML for exact shape attributes.
