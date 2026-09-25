# Pocket/src/main/res/values/dimensions.xml

## What this is

This file fixes shared sizes (23 dimensions): dialog bounds, player heights, card widths, chip metrics. Centralizing them keeps spacing consistent and makes tablet overrides one-file changes.

## How it fits

Layouts and styles consume them as `@dimen/<name>`; tablet/large-screen variants in `values-sw400dp/` and `values-large/` override these where present.

## Key pieces

- `@dimen/bottom_nav_height` (63dp): fixed size used by layouts/styles.
- `@dimen/webview_progress_height` (3dp): fixed size used by layouts/styles.
- `@dimen/dialog_min_padding` (33dp): fixed size used by layouts/styles.
- `@dimen/dialog_max_width` (700dp): fixed size used by layouts/styles.
- `@dimen/dialog_max_height` (700dp): fixed size used by layouts/styles.
- `@dimen/suggested_tags_loading_min_height` (64dp): fixed size used by layouts/styles.
- `@dimen/circle_progress_diameter` (55dp): fixed size used by layouts/styles.
- `@dimen/chip_tag_height` (24dp): fixed size used by layouts/styles.
- `@dimen/chip_text` (16dp): fixed size used by layouts/styles.
- `@dimen/chip_layout_spacing` (6dp): fixed size used by layouts/styles.
- `@dimen/pref_inner_cell_padding_horizontal` (18dp): fixed size used by layouts/styles.
- `@dimen/bottom_nav_elevation` (8dp): fixed size used by layouts/styles.
- `@dimen/listen_mini_player_height` (68dp): fixed size used by layouts/styles.
- `@dimen/listen_space_lg` (24dp): fixed size used by layouts/styles.
- `@dimen/listen_scrub_button_size` (10dp): fixed size used by layouts/styles.
- `@dimen/listen_speed_popup_height` (126dp): fixed size used by layouts/styles.
- `@dimen/listen_speed_popup_width` (54dp): fixed size used by layouts/styles.
- `@dimen/rainbow_bar_height` (3dp): fixed size used by layouts/styles.
- `@dimen/home_max_width` (775dp): fixed size used by layouts/styles.
- `@dimen/home_minor_card_title_and_image_height` (86dp): fixed size used by layouts/styles.
- `@dimen/home_minor_card_width` (312dp): fixed size used by layouts/styles.
- `@dimen/saves_image_width` (90dp): fixed size used by layouts/styles.
- `@dimen/saves_image_height` (60dp): fixed size used by layouts/styles.
