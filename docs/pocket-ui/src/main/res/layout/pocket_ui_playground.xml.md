# pocket-ui/src/main/res/layout/pocket_ui_playground.xml
## What this is
This file is the `pocket_ui_playground` layout: the developer playground screen that renders one of every pocket-ui widget (palette swatches, buttons, app bars, feed footers, skeletons, snackbars, paging views) in a single scroll for visual QA. View-binding (a generated typed class `PocketUiPlaygroundBinding` exposing each `@+id` view without `findViewById`) inflates it.
## How it fits
It is inflated only by `Pocket/src/main/java/com/pocket/app/PocketUiPlaygroundActivity.java` (via `PocketUiPlaygroundBinding`), a dev-only activity never shown to users. Upstream nothing in the production flow hosts it; downstream the ids below surface as typed fields the playground activity wires to demo data and a log-out button, so designers verify theme and spacing changes here before they ship.
Inventory:
- Root: `com.pocket.ui.view.themed.ThemedConstraintLayout`.
- Uses `com.pocket.ui.view.AppBar`.
- Uses `com.pocket.ui.view.PaletteView`.
- Uses `com.pocket.ui.view.button.BoxButton`.
- Uses `com.pocket.ui.view.button.ErrorButton`.
- Uses `com.pocket.ui.view.button.IconButton`.
- Uses `com.pocket.ui.view.button.OnColorButton`.
- Uses `com.pocket.ui.view.button.SubmitButton`.
- Uses `com.pocket.ui.view.info.FeedFooterView`.
- `@+id/app_bar`.
- `@+id/scroll`.
- `@+id/components`.
- `@+id/palette`.
- `@+id/feed_footer`.
- `@+id/discover_item1`.
- `@+id/skeleton1`.
- `@+id/defaultButton`.
- `@+id/errorButton`.
- `@+id/ph1`.
- `@+id/ph2`.
- `@+id/ph3`.
- `@+id/ph4`.
- `@+id/item_snackbar`.
- `@+id/infoPagingView`.
- `@+id/logOut`.
- `@+id/appbar1`.
- `@+id/appbar2`.
- `@+id/appbar3`.
- `@+id/appbar4`.
- ...and 1 more ids (see XML).
