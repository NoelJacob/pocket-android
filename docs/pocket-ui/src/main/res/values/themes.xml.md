# pocket-ui/src/main/res/values/themes.xml
## What this is
This file defines the app themes: `Theme.Pkt.Light` and `Theme.Pkt.Dark` (plus their `PktBase` parents) built on AppCompat with no action bar. They set the status-bar color, remove the window drop shadow, and wire pocket-ui defaults (`iconButtonStyle`, `simpleDrawerRowStyle`, `subheaderStyle`) so every themed view inherits the design system. Night mode flips between the Light and Dark variants. 
## How it fits
The app manifest and activities select `Theme.Pkt.*`; every pocket-ui custom view and layout inherits colors, styles, and defaults from the active theme. `values-v23/themes.xml` further patches status-bar icons on API 23+. Downstream switching Light/Dark repaints all `pkt_themed_*` colors, `cl_*` drawables, and logo variants at once.
Inventory:
- Themes: `Theme.PktBase.Light`, `Theme.PktBase.Dark`, `Theme.Pkt.Light`, `Theme.Pkt.Dark`.
- `android:statusBarColor` = `@color/pkt_grey_3`.
- `android:windowContentOverlay` = `@null`.
- `android:statusBarColor` = `@color/pkt_dm_base_bg`.
- `android:windowContentOverlay` = `@null`.
- `android:cacheColorHint` = `@color/transparent`.
- `android:fadingEdge` = `none`.
- `android:windowNoTitle` = `true`.
- `android:windowContentOverlay` = `@null`.
- `android:colorEdgeEffect` = `@color/black`.
- `iconButtonStyle` = `@style/Pkt_IconButton`.
- `simpleDrawerRowStyle` = `@style/SimpleDrawerRow`.
- `subheaderStyle` = `@style/Subheader`.
- ...and 8 more items (see XML).
