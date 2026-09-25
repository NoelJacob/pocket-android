# Pocket/src/main/res/menu/reader_text_selection.xml

## What this is

Context menu for selected text in the reader: highlight, share, copy, translate, web search, select-all. Each `<item>` is one row: an id for click handling, a title string, and optionally an icon plus `showAsAction` (whether it sits directly in the bar or overflows).

## How it fits

Inflated by `ArticleActionModeCallback` via `MenuInflater`/`Toolbar.inflateMenu`; clicks route back to that host by item id, which dispatches highlight/share/copy/translate/search/select-all.

## Key pieces

- `menu_highlight` (@string/ac_highlight): menu entry; handled by the host's `on...Selected` callback.
- `menu_share` (@string/ac_share): menu entry; handled by the host's `on...Selected` callback.
- `menu_copy` (@string/ac_copy): menu entry; handled by the host's `on...Selected` callback.
- `menu_translate` (@string/ac_translate): menu entry; handled by the host's `on...Selected` callback.
- `menu_web_search` (@string/ac_web_search): menu entry; handled by the host's `on...Selected` callback.
- `menu_select_all` (@string/ac_select_all): menu entry; handled by the host's `on...Selected` callback.
