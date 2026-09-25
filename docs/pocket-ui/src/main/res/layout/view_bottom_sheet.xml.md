# pocket-ui/src/main/res/layout/view_bottom_sheet.xml
## What this is
This file is the `view_bottom_sheet` layout: the XML blueprint for a reusable pocket-ui widget. Its root is `<merge>` (a tag contributing no view of its own; children attach directly to the inflating parent, so the custom view class supplies layout params). View-binding (a generated typed class like `ViewBottomSheetBinding` exposing each `@+id` view without `findViewById`) inflates it. It declares child views and styling hooks; the matching custom view class supplies behavior and data. 
## How it fits
It is inflated by `pocket-ui/src/main/java/com/pocket/ui/view/bottom/BottomDrawer.java` (via `ViewBottomSheetBinding`). Upstream, fragments and parent views in the `Pocket` app module (My List rows, save flow, reader, settings, dialogs) host that custom view; downstream the ids below surface as typed fields the view class binds data, listeners, and tint/theme calls to. 
Inventory:
- Root: `merge`.
- Uses `com.pocket.ui.view.button.IconButton`.
- Uses `com.pocket.ui.view.themed.ThemedConstraintLayout`.
- Uses `com.pocket.ui.view.themed.ThemedLinearLayout`.
- Uses `com.pocket.ui.view.themed.ThemedTextView`.
- `@+id/bottom_sheet_scrim`.
- `@+id/bottom_sheet_content`.
- `@+id/bottom_sheet_nav`.
- `@+id/bottom_sheet_back`.
- `@+id/bottom_sheet_title`.
