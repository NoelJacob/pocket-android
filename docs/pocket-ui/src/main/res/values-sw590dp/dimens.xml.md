# pocket-ui/src/main/res/values-sw590dp/dimens.xml
## What this is
This file overrides spacing for large screens. The `sw590dp` qualifier (smallest-width 590dp: Android uses this folder only on tablets and other wide devices, otherwise it uses plain `values/dimens.xml`) widens side margins and the bottom-sheet margin so content does not stretch edge to edge. 
## How it fits
It redefines the same `@dimen/<name>` keys as `values/dimens.xml` (side grid, bottom-sheet margin); layouts keep referencing the plain names and Android substitutes these values on wide devices. Downstream list rows, cards, and bottom sheets gain roomier gutters on tablets with no code change.
Inventory:
- 4 dimens: `pkt_side_sm`=`@dimen/pkt_space_md`, `pkt_side_grid`=`@dimen/pkt_space_lg`, `pkt_side_grid_icon_mini`=`23dp`, `pkt_bottom_sheet_margin`=`@dimen/pkt_space_lg`
