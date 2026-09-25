# Pocket/src/main/java/com/pocket/app/reader/internal/article/javascript/JavascriptFunctions.kt
## What this is
This is the catalog of every JavaScript command the app can send to the article page: loading content, applying display settings, managing highlights, searching text, and loading images and videos. It is a Kotlin `object` (a singleton with no instances) of small factory functions, each returning a ready-to-execute command string built with `JavascriptFunction`. If the reader needs the page to do something new, the function gets added here.
## How it fits
`ArticleViewModel` calls these when content arrives (`loadCallback`), settings change (`newFontSize`, `newTextStyle`, ...), or highlight/search flows run (`highlightAnnotations`, `searchForText`); `FindTextViewModel` uses the search subset. Commands travel as `ArticleScreen.Event.ExecuteJavascript` to `ArticleFragment`, which executes them in `ArticleWebView`, where the page-side `article.*` handlers act on the live DOM.
## Key pieces
- `load(displaySettingsManager, theme, density, classKey, sdkInt)` — the page-setup call: ships font, size, justification (as 1/0), theme, line height, and device info so first render already matches user settings with no visible restyle.
- `loadCallback(html) / loadImage(articleImage) / loadVideo(videoJson)` — the content pipeline: full HTML first, then per-image (id, local file URL, caption, credit) and per-video payloads as they stream in from `ArticleRepository`, plus `requestContentHeight` to keep scroll measurements fresh.
- `requestAnnotationPatch / highlightAnnotations / scrollToAnnotation` — the highlight round-trip: ask the page for the selected range's patch, paint saved highlights from JSON, scroll to one by id (with a legacy 0 offset kept for signature stability).
## Junior notes
- Each function name must exactly match a handler in the bundled `article-mobile.html` assets — renaming here without updating the HTML silently no-ops the feature.
- `load` reads live values out of `DisplaySettingsManager` at call time, so it always reflects current settings; the `new*` updaters exist so later changes avoid a full reload.
