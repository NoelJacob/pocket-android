# pocket-ui/src/main/res/color/pkt_snackbar_text.xml
## What this is
This file is the `pkt_snackbar_text` themed state-list color. A state-list color (a `selector` XML where Android picks the first listed color whose state matches the view) lets one reference adapt to state instead of hardcoding hex. It maps one semantic color to a light value and a dark-mode value, so themed views (custom `ThemedTextView`/`ThemedImageView` subclasses that understand the custom `app:state_dark` attribute) switch palettes automatically. 
## How it fits
It is referenced from pocket-ui layouts, drawables, or styles as `@color/pkt_snackbar_text`. Upstream a layout or custom themed view sets it as a text, tint, or background color; downstream Android resolves the first matching `<item>` at draw time, so the view repaints when night mode toggles or the widget state changes. 
Entries:
- No `<item>` rows; single value.
