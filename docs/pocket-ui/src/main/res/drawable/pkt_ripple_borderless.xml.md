# pocket-ui/src/main/res/drawable/pkt_ripple_borderless.xml
## What this is
This file is the `pkt_ripple_borderless` ripple drawable (animated touch-feedback ring). It renders fill `@color/pkt_themed_grey_6` behind chips, cards, snackbars, bottom sheets, skeleton placeholders, or badge pills, keeping fills and corner radii consistent instead of each layout inventing its own. 
## How it fits
It is set as `android:background` on the corresponding custom view or layout. Known references: layouts/drawables: `Pocket/src/main/res/layout/view_previous_dark.xml`, `Pocket/src/main/res/layout/view_previous_light.xml`, `pocket-ui/src/main/res/layout/view_dialog_popup.xml`, `pocket-ui/src/main/res/layout/view_pkt_snackbar.xml`. Downstream it is pure visuals: it draws pixels behind content and produces no data.
Inventory:
- Fill: `@color/pkt_themed_grey_6`.
