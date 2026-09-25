# pocket-ui/src/main/res/drawable-xxhdpi/pkt_popup_bg.9.png
## What this is
This file is a nine-patch popup background (`*.9.png`: a PNG with a 1-pixel stretchable border telling Android which rows/columns may stretch and where content may sit, so a single small asset frames any size dialog without distortion). It draws the rounded popup/menu panel behind dropdowns and overflow menus. 
## How it fits
It is used as the background of popup windows and menus in pocket-ui (see `cl_pkt_popup_bg.xml` and dialog/bottom-sheet styles that wrap it). Upstream a `PopupWindow` or menu inflates with this background; downstream Android stretches only the marked regions, so the panel keeps crisp corners at any size. Binary provenance: checked-in design asset under `pocket-ui`; never edited as text.
Inventory:
- Binary 9-patch asset (`xxhdpi` density); contents not listed.
