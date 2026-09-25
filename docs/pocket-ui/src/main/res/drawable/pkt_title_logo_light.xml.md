# pocket-ui/src/main/res/drawable/pkt_title_logo_light.xml
## What this is
This file is the `pkt_title_logo_light` shape drawable (a rectangle/pill drawn in XML). It renders container styling (padding, stroke, or state rows; see inventory) behind chips, cards, snackbars, bottom sheets, skeleton placeholders, or badge pills, keeping fills and corner radii consistent instead of each layout inventing its own. 
## How it fits
It is set as `android:background` on the corresponding custom view or layout. Known references: layouts/drawables: `pocket-ui/src/main/res/drawable/pkt_title_logo.xml`. Downstream it is pure visuals: it draws pixels behind content and produces no data.
Inventory:
- See XML for exact shape attributes.
