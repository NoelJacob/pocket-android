# pocket-ui/src/main/res/drawable/cl_pkt_themed_coral_2.xml
## What this is
This file is the `cl_pkt_themed_coral_2` color-list drawable (a `selector` under `drawable/`: like a state-list color, but usable anywhere a drawable is required, such as a view background). It picks a fill color by theme or widget state so one background reference covers light and dark mode. 
## How it fits
It is used as `android:background` on themed containers or inside other stateful drawables. Known references: layouts/drawables: `pocket-ui/src/main/res/layout/pocket_ui_playground.xml`. Downstream Android swaps the resolved color when night mode or the widget state changes, with no Kotlin involvement.
Entries:
- Single color.
