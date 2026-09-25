# pocket-ui/src/main/res/drawable/cl_pkt_bg.xml
## What this is
This file is the `cl_pkt_bg` color-list drawable (a `selector` under `drawable/`: like a state-list color, but usable anywhere a drawable is required, such as a view background). It picks a fill color by theme or widget state so one background reference covers light and dark mode. 
## How it fits
It is used as `android:background` on themed containers or inside other stateful drawables. Known references: code: `Pocket/src/main/java/com/pocket/util/android/webkit/BaseWebView.java`; layouts/drawables: `Pocket/src/main/res/layout/view_list_item_row.xml`, `Pocket/src/main/res/layout/view_listen.xml`, `pocket-ui/src/main/res/layout/pocket_ui_playground.xml`, `pocket-ui/src/main/res/layout/view_dialog_popup.xml`. Downstream Android swaps the resolved color when night mode or the widget state changes, with no Kotlin involvement.
Entries:
- Single color.
