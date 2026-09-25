# Pocket/src/main/java/com/pocket/app/reader/internal/article/textselection/ArticleActionModeCallback.kt
## What this is
Defines the floating toolbar that appears when the user long-presses and selects text in the article WebView. It offers Copy, Share, Translate, Web search, Select all, and Highlight on top of the WebView's default menu.
## How it fits
Installed on the article WebView by the article screen, wrapping the WebView's own `ActionMode.Callback` (Android's interface for a contextual action bar). When the user picks an item, it grabs the selected text out of the WebView asynchronously and routes it: clipboard for copy, a callback lambda for share/highlight, Google Translate app or a web-search intent for the rest.
## Key pieces
- `setupActionMode(mode, menu)` — clears and re-inflates `R.menu.reader_text_selection` on every create/prepare, and hides the Translate entry when Google Translate isn't installed.
- `onActionItemClicked(mode, item)` — the whole dispatch: `menu_copy` writes to `Clipboard` then dismisses; `menu_share` passes text to `onShareActionModeClicked`; `menu_translate` fires the Translate app; `menu_web_search` fires `ACTION_WEB_SEARCH`; `menu_select_all` selects all via `WebViewUtil`; `menu_highlight` fires `onHighlightActionModeClicked`. Returns false for unknown items so the wrapped callback can handle them.
- `onGetContentRect(...)` — forwards positioning to the wrapped callback when it supports `Callback2`, so the floating bar anchors near the selection.
- `onDestroyActionMode` — intentionally a no-op.
## Junior notes
- `WebViewUtil.getSelectedText` is callback-based (JavaScript bridge into the page), so every action reads the text inside the callback — don't try to read it synchronously.
- `ActionMode.Callback2` vs `Callback`: the `2` variant adds the content-rect positioning used by floating toolbars; the cast-and-forward keeps WebView internals working.
