# pocket-ui/src/main/res/drawable/bg_error_snack_bar.xml
## What this is
This file is the `bg_error_snack_bar` shape drawable (a rectangle/pill drawn in XML). It renders fill `@color/pkt_themed_amber_5`, corner radius `4dp` behind chips, cards, snackbars, bottom sheets, skeleton placeholders, or badge pills, keeping fills and corner radii consistent instead of each layout inventing its own. 
## How it fits
It is set as `android:background` on the corresponding custom view or layout. Known references: code: `Pocket/src/main/java/com/pocket/app/home/views/HomeErrorSnackBar.kt`. Downstream it is pure visuals: it draws pixels behind content and produces no data.
Inventory:
- Fill: `@color/pkt_themed_amber_5`.
- Corners: `4dp`.
- Stroke: `<stroke android:color="@color/pkt_themed_amber_3" android:width="1dp"/>`.
