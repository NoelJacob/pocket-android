# pocket-ui/src/main/res/drawable/ic_circle.xml
## What this is
This file is the `ic_circle` vector icon (a `vector` XML: resolution-independent line art Android rasterizes at any density, so one file serves all screens). It draws the circle dot glyph used on buttons, rows, and toolbars across the app. 
## How it fits
It is set as `android:src`/`app:srcCompat` on `ThemedImageView` or an icon-button style and tinted at runtime via `app:tint`/`drawableColor`, so the same path art adapts to light and dark themes. Known callers include layouts/drawables: `pocket-ui/src/main/res/layout/view_bottom_navigation_button.xml`, `pocket-ui/src/main/res/layout/view_chip.xml`. Downstream it renders pixels only; it produces no data or callbacks.
Inventory:
- Size: `6dp x 6dp`.
- Viewport: `6 x 6`.
- Baked fill colors: `#C4C4C4` (usually overridden by tint).
