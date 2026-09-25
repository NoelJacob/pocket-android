# pocket-ui/src/main/res/layout/view_empty.xml
## What this is
This file is the `view_empty` layout: the XML blueprint for a reusable pocket-ui widget. View-binding (a generated typed class `ViewEmptyBinding` exposing each `@+id` view without `findViewById`) inflates it into its host. It declares child views and styling hooks; the matching custom view class supplies behavior and data. 
## How it fits
It is inflated by `pocket-ui/src/main/java/com/pocket/ui/view/empty/EmptyView.java` (via `ViewEmptyBinding`). Upstream, fragments and parent views in the `Pocket` app module (My List rows, save flow, reader, settings, dialogs) host that custom view; downstream the ids below surface as typed fields the view class binds data, listeners, and tint/theme calls to. 
Inventory:
- Root: `com.pocket.ui.view.visualmargin.VisualMarginConstraintLayout`.
- Uses `androidx.constraintlayout.widget.Barrier`.
- Uses `androidx.constraintlayout.widget.ConstraintLayout`.
- Uses `com.pocket.ui.view.button.BoxButton`.
- Uses `com.pocket.ui.view.button.ErrorButton`.
- Uses `com.pocket.ui.view.themed.ThemedTextView`.
- Uses `com.pocket.ui.view.themed.ThemedView`.
- Uses `com.pocket.ui.view.visualmargin.VisualMarginConstraintLayout`.
- `@+id/message_info`.
- `@+id/title`.
- `@+id/message`.
- `@+id/button`.
- `@+id/error_button`.
- `@+id/content_barrier`.
- `@+id/detail_divider`.
- `@+id/details`.
- `@+id/animation_container`.
