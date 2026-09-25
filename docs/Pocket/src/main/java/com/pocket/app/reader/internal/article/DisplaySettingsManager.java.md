# Pocket/src/main/java/com/pocket/app/reader/internal/article/DisplaySettingsManager.java
## What this is
This is the singleton store for article display settings: font choice and size, line height, margins, justification, theme, and brightness. It persists each setting per user, notifies registered listeners when anything changes, and computes derived values (image width, horizontal margin, typeface) the article WebView needs. It is the single source of truth behind the text-settings bottom sheet.
## How it fits
Hilt DI builds it once with `Preferences`, `AppPrefs`, `Theme`, `PremiumReader`, and version info; `ArticleViewModel` registers as a listener and translates each change into a `JavascriptFunctions` command for live re-render, while `ArticleFragment.ArticleJsInterface` pulls `getHorizontalMargin`/`getImageWidth` from it during page load. Font/margin premium gating consults `PremiumReader.isEnabled()`.
## Key pieces
- `FontOption` enum — the ten fonts (Blanco/Graphik free, the rest premium with `previewFont`, `sizeModifier`, analytics name); `getTypeface` maps the stored id to an Android `Typeface`.
- `increment/decrementFontSize|LineHeight|Margin` + `setFont/setTheme/setBrightness` — stepped setters clamped to resource-defined ranges; each fires the matching `OnDisplaySettingsChangedListener` callback so the WebView updates without reload.
- `getHorizontalMargin(webview)` / `getImageWidth(activity)` — derived layout values: premium users get their margin, others a default; image width accounts for phone vs tablet longest edge. These feed the JS `load` call.
- `addListener/removeListener/invokeListener` — the observer list (used by `ArticleViewModel`); `revertFontPreview` snaps a non-premium user back to the default font if they previewed a premium one.
## Junior notes
- "Web px" in the margin comments means CSS/JS pixels, not screen pixels — never mix them with `DimenUtil` dp conversions without going through the provided getters.
- The 7.3.0 upgrade migration maps the old serif boolean onto Graphik/Blanco ids; that one-time branch must stay until all installs have migrated past it.
