# pocket-ui/src/main/res/drawable/ic_radio_button_unchecked.xml
## What this is
This file is the `ic_radio_button_unchecked` vector icon (a `vector` XML: resolution-independent line art Android rasterizes at any density, so one file serves all screens). It draws the radio button glyph used on buttons, rows, and toolbars across the app. 
## How it fits
It is set as `android:src`/`app:srcCompat` on `ThemedImageView` or an icon-button style and tinted at runtime via `app:tint`/`drawableColor`, so the same path art adapts to light and dark themes. Known callers include layouts/drawables: `pocket-ui/src/main/res/drawable/btn_radio.xml`. Downstream it renders pixels only; it produces no data or callbacks.
Inventory:
- Size: `22dp x 22dp`.
- Viewport: `22 x 22`.
- Baked fill colors: `#1A1A1A` (usually overridden by tint).
