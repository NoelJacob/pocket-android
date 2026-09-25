# pocket-ui/src/main/res/drawable/bg_badge_tag_emphasized.xml
## What this is
This file is the `bg_badge_tag_emphasized` shape drawable (a rectangle/pill drawn in XML). It renders fill `@color/pkt_badge_tag_emphasized_background`, corner radius `4dp` behind chips, cards, snackbars, bottom sheets, skeleton placeholders, or badge pills, keeping fills and corner radii consistent instead of each layout inventing its own. 
## How it fits
It is set as `android:background` on the corresponding custom view or layout. Known references: code: `pocket-ui/src/main/java/com/pocket/ui/view/badge/BadgeView.kt`. Downstream it is pure visuals: it draws pixels behind content and produces no data.
Inventory:
- Fill: `@color/pkt_badge_tag_emphasized_background`.
- Corners: `4dp`.
