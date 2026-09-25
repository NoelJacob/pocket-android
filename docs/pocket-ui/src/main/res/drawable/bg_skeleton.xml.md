# pocket-ui/src/main/res/drawable/bg_skeleton.xml
## What this is
This file is the `bg_skeleton` layer-list drawable (stacks several shapes). It renders fill `@color/pkt_themed_grey_6`, corner radius `10dp` behind chips, cards, snackbars, bottom sheets, skeleton placeholders, or badge pills, keeping fills and corner radii consistent instead of each layout inventing its own. 
## How it fits
It is set as `android:background` on the corresponding custom view or layout. Known references: layouts/drawables: `Pocket/src/main/res/layout/view_home_tablet_slate_hero_card_skeleton.xml`, `Pocket/src/main/res/layout/view_home_tablet_slate_skeleton.xml`. Downstream it is pure visuals: it draws pixels behind content and produces no data.
Inventory:
- Fill: `@color/pkt_themed_grey_6`.
- Corners: `10dp`.
