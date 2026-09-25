# pocket-ui/src/main/res/layout/view_feed_footer.xml
## What this is
This file is the `view_feed_footer` layout: the XML blueprint for a reusable pocket-ui widget. Its root is `<merge>` (a tag contributing no view of its own; children attach directly to the inflating parent, so the custom view class supplies layout params). View-binding (a generated typed class like `ViewFeedFooterBinding` exposing each `@+id` view without `findViewById`) inflates it. It declares child views and styling hooks; the matching custom view class supplies behavior and data. 
## How it fits
It is inflated by `pocket-ui/src/main/java/com/pocket/ui/view/info/FeedFooterView.kt` (via `ViewFeedFooterBinding`). Upstream, fragments and parent views in the `Pocket` app module (My List rows, save flow, reader, settings, dialogs) host that custom view; downstream the ids below surface as typed fields the view class binds data, listeners, and tint/theme calls to. 
Inventory:
- Root: `merge`.
- Uses `com.pocket.ui.view.animated.BottomFeedAnimationView`.
- Uses `com.pocket.ui.view.themed.ThemedTextView`.
- `@+id/text`.
- `@+id/animation`.
