# pocket-ui/src/main/res/drawable/ic_looking.xml
## What this is
This file is the `ic_looking` vector icon (a `vector` XML: resolution-independent line art Android rasterizes at any density, so one file serves all screens). It draws the looking/empty-state illustration glyph used on buttons, rows, and toolbars across the app. 
## How it fits
It is set as `android:src`/`app:srcCompat` on `ThemedImageView` or an icon-button style and tinted at runtime via `app:tint`/`drawableColor`, so the same path art adapts to light and dark themes. It is pulled in by pocket-ui layouts and the `Pkt_IconButton_*` styles in `values/styles.xml` wherever that action appears. Downstream it renders pixels only; it produces no data or callbacks.
Inventory:
- Size: `243dp x 146dp`.
- Viewport: `243 x 146`.
- Baked fill colors: `#000000`, `#FBB54B`, `#ffffff` (usually overridden by tint).
