# pocket-ui/src/main/res/drawable/ic_viewed_not.xml
## What this is
This file is the `ic_viewed_not` vector icon (a `vector` XML: resolution-independent line art Android rasterizes at any density, so one file serves all screens). It draws the eye/viewed glyph used on buttons, rows, and toolbars across the app. 
## How it fits
It is set as `android:src`/`app:srcCompat` on `ThemedImageView` or an icon-button style and tinted at runtime via `app:tint`/`drawableColor`, so the same path art adapts to light and dark themes. Known callers include code: `Pocket/src/main/java/com/pocket/app/home/saves/overflow/RecentSavesOverflowFragment.kt`, `Pocket/src/main/java/com/pocket/app/list/list/overflow/ItemOverflowBottomSheetViewModel.kt`, `Pocket/src/main/java/com/pocket/app/reader/internal/originalweb/overlay/bottomsheet/OriginalWebBottomSheetViewModel.kt`, `Pocket/src/main/java/com/pocket/app/reader/toolbar/OverflowBuilder.kt`; layouts/drawables: `Pocket/src/main/res/layout/fragment_original_web_bottom_sheet.xml`. Downstream it renders pixels only; it produces no data or callbacks.
Inventory:
- Size: `24dp x 24dp`.
- Viewport: `24 x 24`.
- Baked fill colors: `#00000000`, `#1A1A1A` (usually overridden by tint).
