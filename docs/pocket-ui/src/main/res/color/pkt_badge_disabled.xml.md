# pocket-ui/src/main/res/color/pkt_badge_disabled.xml
## What this is
This file is the `pkt_badge_disabled` themed state-list color. A state-list color (a `selector` XML where Android picks the first listed color whose state matches the view) lets one reference adapt to state instead of hardcoding hex. It maps one semantic color to a light value and a dark-mode value, so themed views (custom `ThemedTextView`/`ThemedImageView` subclasses that understand the custom `app:state_dark` attribute) switch palettes automatically. 
## How it fits
It is referenced by code: `pocket-ui/src/main/java/com/pocket/ui/view/badge/BadgeDrawable.java`. Upstream, layout XML or a custom view sets it as `android:textColor`, `app:tint`, or a background tint; downstream Android resolves the first matching `<item>` at draw time and repaints the view when night mode toggles or the widget state changes. 
Entries:
- No `<item>` rows; single value.
