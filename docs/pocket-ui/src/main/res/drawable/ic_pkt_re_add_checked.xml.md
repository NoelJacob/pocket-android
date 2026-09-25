# pocket-ui/src/main/res/drawable/ic_pkt_re_add_checked.xml
## What this is
This file is the `ic_pkt_re_add_checked` vector icon (a `vector` XML: resolution-independent line art Android rasterizes at any density, so one file serves all screens). It draws the re-add glyph used on buttons, rows, and toolbars across the app. 
## How it fits
It is set as `android:src`/`app:srcCompat` on `ThemedImageView` or an icon-button style and tinted at runtime via `app:tint`/`drawableColor`, so the same path art adapts to light and dark themes. Known callers include layouts/drawables: `pocket-ui/src/main/res/values/styles.xml`. Downstream it renders pixels only; it produces no data or callbacks.
Inventory:
- Size: standard 24dp icon.
- Baked fill colors: none (fully tinted).
