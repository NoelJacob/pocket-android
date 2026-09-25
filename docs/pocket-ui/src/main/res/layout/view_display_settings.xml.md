# pocket-ui/src/main/res/layout/view_display_settings.xml
## What this is
This file is the `view_display_settings` layout: the XML blueprint for a reusable pocket-ui widget. Its root is `<merge>` (a tag contributing no view of its own; children attach directly to the inflating parent, so the custom view class supplies layout params). View-binding (a generated typed class like `ViewDisplaySettingsBinding` exposing each `@+id` view without `findViewById`) inflates it. It declares child views and styling hooks; the matching custom view class supplies behavior and data. 
## How it fits
It is inflated by `pocket-ui/src/main/java/com/pocket/ui/view/menu/DisplaySettingsView.java` (via `ViewDisplaySettingsBinding`). Upstream, fragments and parent views in the `Pocket` app module (My List rows, save flow, reader, settings, dialogs) host that custom view; downstream the ids below surface as typed fields the view class binds data, listeners, and tint/theme calls to. 
Inventory:
- Root: `merge`.
- Uses `androidx.constraintlayout.widget.Barrier`.
- Uses `com.pocket.ui.view.button.IconButton`.
- Uses `com.pocket.ui.view.checkable.CheckableTextView`.
- Uses `com.pocket.ui.view.menu.SettingIncrementor`.
- Uses `com.pocket.ui.view.menu.ThemeToggle`.
- Uses `com.pocket.ui.view.settings.PocketSeekBar`.
- Uses `com.pocket.ui.view.themed.ThemedConstraintLayout`.
- Uses `com.pocket.ui.view.themed.ThemedImageView`.
- `@+id/settings_theme`.
- `@+id/settings_divider_1`.
- `@+id/settings_brightness_down`.
- `@+id/settings_brightness_slider`.
- `@+id/settings_brightness_up`.
- `@+id/settings_divider_2`.
- `@+id/settings_size_non_premium`.
- `@+id/settings_text_size_divider_non_premium`.
- `@+id/barrier`.
- `@+id/settings_change_font`.
- `@+id/fonts_chevron`.
- `@+id/settings_divider_3`.
- `@+id/premium_settings`.
- `@+id/settings_size`.
- `@+id/settings_line_height`.
- `@+id/settings_margin`.
- `@+id/premium_upgrade`.
- `@+id/upgrade_icon`.
- `@+id/upgrade_text`.
