# pocket-ui/src/main/res/color/pkt_themed_grey_1.xml
## What this is
This file is the `pkt_themed_grey_1` themed state-list color. A state-list color (a `selector` XML where Android picks the first listed color whose state matches the view) lets one reference adapt to state instead of hardcoding hex. It maps one semantic color to a light value and a dark-mode value, so themed views (custom `ThemedTextView`/`ThemedImageView` subclasses that understand the custom `app:state_dark` attribute) switch palettes automatically. 
## How it fits
It is referenced by code: `Pocket/src/main/java/com/pocket/app/add/AddOverlayView.java`, `Pocket/src/main/java/com/pocket/app/reader/internal/originalweb/overlay/bottomsheet/OriginalWebBottomSheetViewModel.kt`; layouts/drawables: `Pocket/src/main/res/layout/frag_recommendation_overflow_bottom_sheet.xml`, `Pocket/src/main/res/layout/frag_report_item_bottom_sheet.xml`, `Pocket/src/main/res/layout/frag_sort_filter_bottom_sheet.xml`, `Pocket/src/main/res/layout/frag_tag_bottom_sheet.xml`. Upstream, layout XML or a custom view sets it as `android:textColor`, `app:tint`, or a background tint; downstream Android resolves the first matching `<item>` at draw time and repaints the view when night mode toggles or the widget state changes. 
Entries:
- No `<item>` rows; single value.
