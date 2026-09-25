# Pocket/src/main/res/values/attrs.xml

## What this is

This file declares custom XML attributes (69 attrs across 18 styleables) for the app's custom views: the extra `app:` properties you can set on them in layouts (dimensions, colors, flags).

## How it fits

Custom views read these in their constructors via `obtainStyledAttributes`; layouts set them with the `app:` namespace. If you add a settable property to a custom view, its `<attr>` goes here.

## Key pieces

- `MaxWidthView`: custom-view attribute block (see the `<attr>` entries beneath it in the file).
- `PremiumUpgradeBlockView`: custom-view attribute block (see the `<attr>` entries beneath it in the file).
- `PocketTheme`: custom-view attribute block (see the `<attr>` entries beneath it in the file).
- `IconTypes`: custom-view attribute block (see the `<attr>` entries beneath it in the file).
- `VerticallyCenteredScrollView`: custom-view attribute block (see the `<attr>` entries beneath it in the file).
- `ResizeDetectLinearLayout`: custom-view attribute block (see the `<attr>` entries beneath it in the file).
- `states`: custom-view attribute block (see the `<attr>` entries beneath it in the file).
- `Tooltip`: custom-view attribute block (see the `<attr>` entries beneath it in the file).
- `DialogSizeWrapper`: custom-view attribute block (see the `<attr>` entries beneath it in the file).
- `RecipientEditTextView`: custom-view attribute block (see the `<attr>` entries beneath it in the file).
- `SlidingDrawer`: custom-view attribute block (see the `<attr>` entries beneath it in the file).
- `Switch`: custom-view attribute block (see the `<attr>` entries beneath it in the file).
- `FlowLayout`: custom-view attribute block (see the `<attr>` entries beneath it in the file).
- `FlowLayout_LayoutParams`: custom-view attribute block (see the `<attr>` entries beneath it in the file).
- `ChipEditText`: custom-view attribute block (see the `<attr>` entries beneath it in the file).
- `PocketSeekBar`: custom-view attribute block (see the `<attr>` entries beneath it in the file).
- `PillButton`: custom-view attribute block (see the `<attr>` entries beneath it in the file).
- `Reader`: custom-view attribute block (see the `<attr>` entries beneath it in the file).
