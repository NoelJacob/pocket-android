# pocket-ui/src/main/res/values/strings.xml
## What this is
This file is the pocket-ui base string table: every user-visible label, button caption, content description (the accessibility label screen readers announce), and upsell line owned by the shared UI kit. Keeping them here instead of hardcoded in layouts means translators and TalkBack get one source of truth. 
## How it fits
It is referenced as `@string/<name>` from pocket-ui layouts, styles, and custom views (for example `view_app_bar.xml`, the `Pkt_IconButton_*` styles, and theme-toggle / text-size controls), and each `values-<locale>/strings.xml` file provides the same keys in another language, which Android picks by device locale. Downstream the strings render as text or accessibility announcements; they carry no logic.
Inventory:
- 50 strings, e.g. `ic_overflow`, `ic_up`, `ic_archive`, `ic_save`, `ic_saved`, `ic_readd`, `ic_favorite`, `ic_unfavorite`, `ic_mark_as_viewed`, `ic_mark_as_not_viewed`, `ic_share`, `ic_delete`.
- `ac_*` (4): `ac_save`, `ac_save_to_pocket`, `ac_previous`, `ac_next`.
- `ic_*` (32): `ic_overflow`, `ic_up`, `ic_archive`, `ic_save`, `ic_saved`, `ic_readd`, ....
