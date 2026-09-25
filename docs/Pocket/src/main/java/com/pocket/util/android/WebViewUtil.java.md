# Pocket/src/main/java/com/pocket/util/android/WebViewUtil.java

## What this is
Two helpers for driving text selection inside a `WebView` (the embedded browser view used by the Reader). They solve the gap that WebView selections are not reachable through normal Android APIs: selected text is fetched via injected JavaScript, and select-all is triggered the same way. For example, the Reader's long-press menu calls `getSelectedText` for its Copy, Share, Translate, and Web-search actions, and `selectAll` for Select-all.

## How it fits
Its known caller is `ArticleActionModeCallback`, the handler for the Reader's text-selection action mode: Copy sends the text to `Clipboard.setText`, Share forwards it to a share callback, Translate checks it via `IntentUtils.googleTranslate`, Web-search fires an `ACTION_WEB_SEARCH` intent, and Select-all calls `selectAll`. Results come back through the `SelectedTextCallback`.

## Key pieces
- `getSelectedText(webview, callback)`: evaluates `window.getSelection().toString()`, then cleans the result (trims, strips unescaped quotes, unescapes Java escaping) before delivery. WHY the cleanup: the value arrives JSON/Java-escaped with wrapper quotes, so raw use would show backslashes and stray quote marks.
- `selectAll(webView)`: runs `document.execCommand("selectAll")`. WHY JavaScript: WebView exposes no native select-all API.
- `SelectedTextCallback.onTextSelectionRetrieved(selectedText)`: the async result sink. WHY a callback: `evaluateJavascript` is asynchronous by nature.

## Junior notes
- The callback may arrive off the UI thread (per the Javadoc) — hop back to the main thread before touching views, toasts, or the clipboard UI.
- `evaluateJavascript` only works when the WebView is attached and JavaScript is enabled; a missing selection yields null/empty, which every action-mode branch null-checks.
- `document.execCommand` is deprecated in web standards but remains the working WebView path; if it ever stops working, the replacement is per-element selection via JavaScript Range APIs.
