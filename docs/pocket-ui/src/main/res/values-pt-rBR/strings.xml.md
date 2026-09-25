# pocket-ui/src/main/res/values-pt-rBR/strings.xml
## What this is
This file is the Portuguese (Brazil) (`values-pt-rBR`) translation of the pocket-ui string table. A qualified `values-<locale>` folder (Android picks the folder whose qualifier matches the device language/region, falling back to plain `values/`) lets the same layout show translated text with zero code changes. 
## How it fits
It mirrors every key from `pocket-ui/src/main/res/values/strings.xml` in Portuguese (Brazil). Upstream nothing references it directly; Android resolves `@string/<name>` to this file when the device locale matches, otherwise to the base table. Downstream the translated text appears in pocket-ui widgets (app bar, chips, dialogs, settings rows) wherever the base key is used.
Inventory:
- 50 strings, e.g. `ic_overflow`, `ic_up`, `ic_archive`, `ic_save`, `ic_saved`, `ic_readd`, `ic_favorite`, `ic_unfavorite`, `ic_mark_as_viewed`, `ic_mark_as_not_viewed`, `ic_share`, `ic_delete`.
- `ac_*` (4): `ac_save`, `ac_save_to_pocket`, `ac_previous`, `ac_next`.
- `ic_*` (32): `ic_overflow`, `ic_up`, `ic_archive`, `ic_save`, `ic_saved`, `ic_readd`, ....
