# pocket-ui/src/main/res/color/pkt_themed_coral_2.xml
## What this is
This file is the `pkt_themed_coral_2` themed state-list color. A state-list color (a `selector` XML where Android picks the first listed color whose state matches the view) lets one reference adapt to state instead of hardcoding hex. It maps one semantic color to a light value and a dark-mode value, so themed views (custom `ThemedTextView`/`ThemedImageView` subclasses that understand the custom `app:state_dark` attribute) switch palettes automatically. 
## How it fits
It is referenced by code: `pocket-ui/src/main/java/com/pocket/ui/util/PlaceHolderBuilder.java`, `pocket-ui/src/main/java/com/pocket/ui/view/progress/RainbowProgressCircleView.java`; layouts/drawables: `pocket-ui/src/main/res/color/pkt_button_box_coral_fill.xml`, `pocket-ui/src/main/res/color/pkt_nst_clickable_grey_3_coral_checked.xml`, `pocket-ui/src/main/res/layout/view_bottom_navigation_button.xml`, `pocket-ui/src/main/res/layout/view_chip.xml`. Upstream, layout XML or a custom view sets it as `android:textColor`, `app:tint`, or a background tint; downstream Android resolves the first matching `<item>` at draw time and repaints the view when night mode toggles or the widget state changes. 
Entries:
- No `<item>` rows; single value.
