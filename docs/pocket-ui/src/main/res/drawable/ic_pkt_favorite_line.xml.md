# pocket-ui/src/main/res/drawable/ic_pkt_favorite_line.xml
## What this is
This file is the `ic_pkt_favorite_line` vector icon (a `vector` XML: resolution-independent line art Android rasterizes at any density, so one file serves all screens). It draws the heart/favorite glyph used on buttons, rows, and toolbars across the app. 
## How it fits
It is set as `android:src`/`app:srcCompat` on `ThemedImageView` or an icon-button style and tinted at runtime via `app:tint`/`drawableColor`, so the same path art adapts to light and dark themes. Known callers include code: `Pocket/src/main/java/com/pocket/app/reader/internal/originalweb/overlay/bottomsheet/OriginalWebBottomSheetViewModel.kt`, `Pocket/src/main/java/com/pocket/app/reader/toolbar/OverflowBuilder.kt`; layouts/drawables: `Pocket/src/main/res/layout/view_empty_list_favorites.xml`, `pocket-ui/src/main/res/drawable/ic_pkt_favorite_checked.xml`. Downstream it renders pixels only; it produces no data or callbacks.
Inventory:
- Size: `24dp x 24dp`.
- Viewport: `24 x 24`.
- Baked fill colors: `#828282` (usually overridden by tint).
