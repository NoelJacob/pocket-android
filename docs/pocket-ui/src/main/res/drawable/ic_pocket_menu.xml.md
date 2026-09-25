# pocket-ui/src/main/res/drawable/ic_pocket_menu.xml
## What this is
This file is the `ic_pocket_menu` vector icon (a `vector` XML: resolution-independent line art Android rasterizes at any density, so one file serves all screens). It draws the menu, Pocket logo glyph used on buttons, rows, and toolbars across the app. 
## How it fits
It is set as `android:src`/`app:srcCompat` on `ThemedImageView` or an icon-button style and tinted at runtime via `app:tint`/`drawableColor`, so the same path art adapts to light and dark themes. Known callers include code: `Pocket/src/main/java/com/pocket/app/reader/internal/originalweb/OriginalWebFragment.kt`. Downstream it renders pixels only; it produces no data or callbacks.
Inventory:
- Size: `24dp x 24dp`.
- Viewport: `24 x 24`.
- Baked fill colors: `#000000` (usually overridden by tint).
