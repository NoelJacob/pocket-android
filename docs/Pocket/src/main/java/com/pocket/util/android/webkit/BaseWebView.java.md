# Pocket/src/main/java/com/pocket/util/android/webkit/BaseWebView.java
## What this is
Pocket's shared `WebView` (Android's embedded browser widget) base: JavaScript on, file access off by default, theme-aware background, scroll tracking, rainbow progress bar, frozen-snapshot mode, overlay views, and JS/selection helpers. Subclasses get article-reader behavior without reimplementing WebView plumbing.
For example, showing a purchase page means extending this, loading the URL, and toggling the progress bar while content loads.

## How it fits
Extended by `PremiumUpgradeWebView` for the web purchase flow, and conceptually behind reader article rendering (which drives page JS via the newer `JavascriptFunctions` helpers). It registers for app theme changes through `AbsPocketActivity`, tracks scroll via `ScrollTracker`, and runs UI work through `App.threads().runOrPostOnUiThread`.

## Key pieces
- `init()` + `setFileAccessEnabled()`: JS enabled, file/universal-file-URL access gated. WHY they exist: article JS needs scripting, but file access stays off for security unless explicitly enabled.
- `setFrozen(freeze, animate)` + `draw()` + `setContentVisible()`: snapshot bitmap overlay and loading blocker. WHY they exist: hide white flashes and freezes during article swaps with a static image instead of a blank view.
- `setProgressBarVisibility/setProgress/invalidateProgressBar` (`RainbowBar`): WHY they exist: branded load progress without each screen rebuilding it.
- `execJS(query)` (deprecated): `loadUrl("javascript:...")` on the UI thread. WHY it exists: legacy one-shot JS evaluation; prefer `evaluateJavascript` paths for results.
- `addFixedView` / `addPositionedView`, `setChildFixedHorizontally/Vertically`: overlay pinning against scroll. WHY they exist: keep toolbars or highlights visually anchored while content moves.
- `screenPxToWebPx` / `webPxToScreenPx` / `getMaxContentScrollY` / `lockScrolling`: coordinate conversion and scroll control. WHY they exist: WebView reports content in web pixels at a density-dependent scale, so overlays and clamps must convert.
- `OnContentDisplayedListener`, `OnInteractionListener`, `ResizeListener`, `getSelectedText/stopTextSelection`, `isFlinging`: content, gesture, and selection callbacks. WHY they exist: let hosts react to first paint, taps, resizes, and text selection.

## Junior notes
- `WebView` methods are thread-bound: `loadUrl`/`execJS` must run on the UI thread; this class posts there for you, but never call them from a pool thread expecting a synchronous result.
- `updateThemeManually()` paints the theme background so over-scroll and pre-paint never flash white in dark mode.
- `setFrozen(true)` skips `draw()`; forgetting to unfreeze leaves a stale snapshot on screen, so pair every freeze with an unfreeze path.
