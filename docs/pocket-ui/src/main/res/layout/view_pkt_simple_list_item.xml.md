# pocket-ui/src/main/res/layout/view_pkt_simple_list_item.xml
## What this is
This file is the `view_pkt_simple_list_item` layout: the XML blueprint for a reusable pocket-ui widget. View-binding (a generated typed class `ViewPktSimpleListItemBinding` exposing each `@+id` view without `findViewById`) inflates it into its host. It declares child views and styling hooks; the matching custom view class supplies behavior and data. 
## How it fits
It is inflated by `pocket-ui/src/main/java/com/pocket/ui/view/menu/SimpleOptionsRecyclerView.java` (via `ViewPktSimpleListItemBinding`). Upstream, fragments and parent views in the `Pocket` app module (My List rows, save flow, reader, settings, dialogs) host that custom view; downstream the ids below surface as typed fields the view class binds data, listeners, and tint/theme calls to. 
Inventory:
- Root: `com.pocket.ui.view.themed.ThemedFrameLayout`.
- Uses `com.pocket.ui.view.themed.ThemedFrameLayout`.
- Uses `com.pocket.ui.view.themed.ThemedTextView`.
- Uses `com.pocket.ui.view.themed.ThemedView`.
