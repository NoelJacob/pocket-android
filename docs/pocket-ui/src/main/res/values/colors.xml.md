# pocket-ui/src/main/res/values/colors.xml
## What this is
This file is the Pocket design-system palette: every raw color in pocket-ui must be defined here (per the header comment pointing at the Figma design-system spec), never inline in layouts. It holds the light palette (`pkt_grey_*`, `pkt_teal_*`, `pkt_coral_*`, `pkt_amber_*`, `pkt_lapis_*`), the dark-mode twins (`pkt_dm_*`), and functional aliases (`white`, `black`, `transparent`, `pkt_base_bg`). Screens reference the themed `res/color/pkt_themed_*` selectors, which map onto these raw values per night mode. 
## How it fits
It is the bottom of the color dependency chain: `res/color/*.xml` selectors and `res/drawable/*` shapes point at these entries via `@color/<name>`, and layouts/styles ultimately resolve through them. Upstream the design team owns the hex values; downstream changing one entry repaints every component using that semantic color in both light and dark themes.
Inventory:
- 59 colors.
- `pkt_grey*` (7): `pkt_grey_1`=`#1A1A1A`, `pkt_grey_2`=`#333333`, `pkt_grey_3`=`#404040`, `pkt_grey_4`=`#737373`, `pkt_grey_5`=`#8C8C8C`, `pkt_grey_6`=`#D9D9D9`, `pkt_grey_7`=`#ECECEC`
- `pkt_teal*` (6): `pkt_teal_1`=`#004D48`, `pkt_teal_2`=`#008078`, `pkt_teal_3`=`#009990`, `pkt_teal_4`=`#80BFBB`, `pkt_teal_5`=`#95D5D2`, `pkt_teal_6`=`#E8F7F6`
- `pkt_coral*` (5): `pkt_coral_1`=`#901424`, `pkt_coral_2`=`#EF4056`, `pkt_coral_3`=`#F79FAA`, `pkt_coral_4`=`#FBCFD4`, `pkt_coral_5`=`#FDECEE`
- `pkt_amber*` (3): `pkt_amber_faint`=`#FFF8EC`, `pkt_amber_3`=`#FF9F00`, `pkt_amber_4`=`#FEE8C3`
- `pkt_lapis*` (4): `pkt_lapis_faint`=`#E8F7FE`, `pkt_lapis_1`=`#1649AC`, `pkt_lapis_3`=`#3668FF`, `pkt_lapis_5`=`#DCEAFF`
- `pkt_dm*` (23): `pkt_dm_base_bg`=`#1A1A1A`, `pkt_dm_grey_1`=`#F2F2F2`, `pkt_dm_grey_2`=`#CCCCCC`, `pkt_dm_grey_3`=`#CCCCCC`, `pkt_dm_grey_4`=`#999999`, `pkt_dm_grey_5`=`#737373`, `pkt_dm_grey_6`=`#404040`, `pkt_dm_grey_7`=`#333333` ...
- `white*` (1): `white`=`#FFFFFF`
- `black*` (1): `black`=`#000000`
- `transparent*` (1): `transparent`=`#00000000`
- `pkt_base*` (1): `pkt_base_bg`=`@color/white`
- `pkt_apricot*` (1): `pkt_apricot_1`=`#B24000`
