# pocket-ui/src/main/res/color/pkt_themed_grey_1_clickable.xml
## What this is
This file is the `pkt_themed_grey_1_clickable` state-list color. A state-list color (a `selector` XML where Android picks the first listed color whose state matches the view) lets one reference adapt to state instead of hardcoding hex. It returns different colors for widget states such as disabled versus enabled or checked versus unchecked, so buttons, checkboxes, and chips visibly change without extra Kotlin code. 
## How it fits
It is referenced by code: `pocket-ui/src/main/java/com/pocket/ui/view/AppBar.java`, `pocket-ui/src/main/java/com/pocket/ui/view/button/PurchaseStateButtons.java`. Upstream, layout XML or a custom view sets it as `android:textColor`, `app:tint`, or a background tint; downstream Android resolves the first matching `<item>` at draw time and repaints the view when night mode toggles or the widget state changes. 
Entries:
- No `<item>` rows; single value.
