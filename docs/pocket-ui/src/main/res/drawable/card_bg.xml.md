# pocket-ui/src/main/res/drawable/card_bg.xml
## What this is
This file is the `card_bg` shape drawable (a rectangle/pill drawn in XML). It renders fill `@color/pkt_card_background` behind chips, cards, snackbars, bottom sheets, skeleton placeholders, or badge pills, keeping fills and corner radii consistent instead of each layout inventing its own. 
## How it fits
It is set as `android:background` on the corresponding custom view or layout. Known references: layouts/drawables: `Pocket/src/main/res/layout/view_home_slate_wide_hero_card.xml`, `Pocket/src/main/res/layout/view_home_tablet_slate_hero_card_skeleton.xml`. Downstream it is pure visuals: it draws pixels behind content and produces no data.
Inventory:
- Fill: `@color/pkt_card_background`.
