# pocket-ui/src/main/res/drawable/ic_filter.xml
## What this is
This file is the `ic_filter` vector icon (a `vector` XML: resolution-independent line art Android rasterizes at any density, so one file serves all screens). It draws the filter funnel glyph used on buttons, rows, and toolbars across the app. 
## How it fits
It is set as `android:src`/`app:srcCompat` on `ThemedImageView` or an icon-button style and tinted at runtime via `app:tint`/`drawableColor`, so the same path art adapts to light and dark themes. Known callers include code: `Pocket/src/main/java/com/pocket/app/PocketUiPlaygroundActivity.java`. Downstream it renders pixels only; it produces no data or callbacks.
Inventory:
- Size: `22dp x 21dp`.
- Viewport: `22 x 21`.
- Baked fill colors: `#1A1A1A` (usually overridden by tint).
