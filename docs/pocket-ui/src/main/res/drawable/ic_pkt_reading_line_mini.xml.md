# pocket-ui/src/main/res/drawable/ic_pkt_reading_line_mini.xml
## What this is
This file is the `ic_pkt_reading_line_mini` vector icon (a `vector` XML: resolution-independent line art Android rasterizes at any density, so one file serves all screens). It draws the UI glyph glyph used on buttons, rows, and toolbars across the app. 
## How it fits
It is set as `android:src`/`app:srcCompat` on `ThemedImageView` or an icon-button style and tinted at runtime via `app:tint`/`drawableColor`, so the same path art adapts to light and dark themes. Known callers include code: `Pocket/src/main/java/com/pocket/util/android/view/ContinueReadingView.java`. Downstream it renders pixels only; it produces no data or callbacks.
Inventory:
- Size: `16dp x 16dp`.
- Viewport: `16 x 16`.
- Baked fill colors: `#828282` (usually overridden by tint).
