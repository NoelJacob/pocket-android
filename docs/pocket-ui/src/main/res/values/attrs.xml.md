# pocket-ui/src/main/res/values/attrs.xml
## What this is
This file declares pocket-ui custom XML attributes (extra `app:` properties, like `app:tint` or `app:typeface`, that custom views read in their constructors) and the `declare-styleable` groups that bundle them per view. It also defines the `typeface` enum mapping Graphik/Blanco/Doyle font names to integer values used by `Fonts.java`. Without these declarations, layouts could not set those properties in XML. 
## How it fits
Layouts set these attributes (for example `ThemedTextView` reads `typeface`/`compatTextColor`, `IconButton` reads `drawableColor`/`isCheckable`, `PocketChip` reads its chip attrs) and the matching view class obtains them via `obtainStyledAttributes` at construction. Upstream the `Pocket` app layouts consume them; downstream they control fonts, tints, checkability, and content descriptions.
Inventory:
- 22 styleable groups, 46 distinct attrs.
- `appTheme`: `state_light`, `state_dark`
- `DefaultStyles`: `simpleDrawerRowStyle`, `subheaderStyle`, `iconButtonStyle`
- `ThemedImageView`: `drawableColor`, `heightRatio`
- `CheckableHelper`: `isCheckable`, `checkedContentDescription`
- `ThemedTextView`: `visualPadding`, `paddingVerticalCenter`, `compatTextColor`, `typeface`
- `SectionHeaderView`: `showDividerTop`, `showDividerBottom`, `android:text`
- `ThemedEditText`: `compatEditTextColor`, `compatEditTextHintColor`, `typeface`
- `IconButton`: `checkedDrawableColor`
- `ItemActionsView`: `actionLayout`
- `BottomDrawer`: `sheetLayout`
- `ItemActionsBarView`: `itemActionBarSidePadding`
- `VisualMarginConstraintLayout_Layout`: `visualMargin_top`, `visualMargin_goneTop`
- `RainbowProgressCircleView`: `progressColorsExcludeCoral`, `progressStartAsArc`
- `CountIconButton`: `countIconSrc`, `countIconColor`
- `SettingsSwitchView`: `android:title`, `android:text`, `android:enabled`, `isToggle`
- `LabeledEditText`: `android:hint`, `android:inputType`, `android:drawableRight`, `underLine`
- `AppBar`: `android:title`, `leftIcon`, `bottomDivider`
- `SkeletonView`: `randomWidthPercentFloor`, `randomWidthPercentCeil`, `compatBackgroundColor`, `cornerRadius`
- `SkeletonParagraphView`: `minLines`, `maxLines`
- `BottomNavigationButton`: `imageSrc`, `labelText`
- `PocketChip`: `chipText`, `chipIcon`, `chipIconUnselected`, `chipSelectable`
- `FilterTile`: `foregroundColor`, `titleText`, `descriptionText`
