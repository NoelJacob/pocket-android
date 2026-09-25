# pocket-ui/src/main/res/color/pkt_nst_clickable_grey_3_coral_checked.xml
## What this is
This file is the `pkt_nst_clickable_grey_3_coral_checked` state-list color. A state-list color (a `selector` XML where Android picks the first listed color whose state matches the view) lets one reference adapt to state instead of hardcoding hex. It returns different colors for widget states such as disabled versus enabled or checked versus unchecked, so buttons, checkboxes, and chips visibly change without extra Kotlin code. 
## How it fits
It is referenced by layouts/drawables: `pocket-ui/src/main/res/values/styles.xml`. Upstream, layout XML or a custom view sets it as `android:textColor`, `app:tint`, or a background tint; downstream Android resolves the first matching `<item>` at draw time and repaints the view when night mode toggles or the widget state changes. 
Entries:
- No `<item>` rows; single value.
