# pocket-ui/src/main/res/drawable/bg_snackbar.xml
## What this is
This file is the `bg_snackbar` shape drawable (a rectangle/pill drawn in XML). It renders fill `@color/pkt_snackbar_background`, corner radius `360dp` behind chips, cards, snackbars, bottom sheets, skeleton placeholders, or badge pills, keeping fills and corner radii consistent instead of each layout inventing its own. 
## How it fits
It is set as `android:background` on the corresponding custom view or layout. Known references: code: `Pocket/src/main/java/com/pocket/app/list/bulkedit/BulkEditSnackBar.kt`. Downstream it is pure visuals: it draws pixels behind content and produces no data.
Inventory:
- Fill: `@color/pkt_snackbar_background`.
- Corners: `360dp`.
