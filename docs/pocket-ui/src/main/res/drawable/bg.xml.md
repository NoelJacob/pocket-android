# pocket-ui/src/main/res/drawable/bg.xml
## What this is
This file is the `bg` shape drawable (a rectangle/pill drawn in XML). It renders fill `@color/pkt_bg` behind chips, cards, snackbars, bottom sheets, skeleton placeholders, or badge pills, keeping fills and corner radii consistent instead of each layout inventing its own. 
## How it fits
It is set as `android:background` on the corresponding custom view or layout. Known references: code: `Pocket/src/main/java/com/pocket/app/PocketUiPlaygroundActivity.java`, `Pocket/src/main/java/com/pocket/app/UserManager.java`, `Pocket/src/main/java/com/pocket/app/add/AddOverlayView.java`, `Pocket/src/main/java/com/pocket/app/home/views/HomeErrorSnackBar.kt`; layouts/drawables: `Pocket/src/main/res/layout/activity_main.xml`, `Pocket/src/main/res/layout/fragment_article.xml`, `Pocket/src/main/res/layout/fragment_collection.xml`, `Pocket/src/main/res/layout/fragment_home.xml`. Downstream it is pure visuals: it draws pixels behind content and produces no data.
Inventory:
- Fill: `@color/pkt_bg`.
