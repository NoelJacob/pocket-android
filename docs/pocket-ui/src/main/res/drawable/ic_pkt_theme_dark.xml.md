# pocket-ui/src/main/res/drawable/ic_pkt_theme_dark.xml
## What this is
This file is the `ic_pkt_theme_dark` vector icon (a `vector` XML: resolution-independent line art Android rasterizes at any density, so one file serves all screens). It draws the theme mode glyph used on buttons, rows, and toolbars across the app. 
## How it fits
It is set as `android:src`/`app:srcCompat` on `ThemedImageView` or an icon-button style and tinted at runtime via `app:tint`/`drawableColor`, so the same path art adapts to light and dark themes. Known callers include layouts/drawables: `pocket-ui/src/main/res/layout/view_theme_toggle.xml`. Downstream it renders pixels only; it produces no data or callbacks.
Inventory:
- Size: `36dp x 36dp`.
- Viewport: `36 x 36`.
- Baked fill colors: `#1a1a1a` (usually overridden by tint).
