# Pocket/src/main/java/com/pocket/app/reader/internal/article/javascript/JavascriptFunction.kt
## What this is
This is the builder for a single JavaScript call into the article page: it takes a function name, accumulates typed parameters with correct escaping, and renders the final `article.name(p1, p2);` command string. Every piece of native-to-WebView communication (load article, change font, highlight, search) is assembled through this class so quoting bugs live in exactly one place.
## How it fits
`JavascriptFunctions` (the catalog of known calls) creates one per invocation, adds parameters, and returns `getCommand()`; `ArticleViewModel` emits that string as `ArticleScreen.Event.ExecuteJavascript`, and `ArticleFragment` runs it via `ArticleWebView.executeJS` (`evaluateJavascript`). The page-side `article.*` object in `article-mobile.html` receives the call.
## Key pieces
- `addParameter(String)` — escapes for safe embedding inside single quotes (Java escaping plus unescaped `'` handling); this is what makes article HTML with quotes and newlines survive the trip into JS.
- `addJsonStringParameter` — passes JSON through unquoted with only U+2028/2029 escaped, since those two characters break JS string parsing even inside valid JSON. Used for highlights and video payloads.
- `addParameter(Int/Float/Double/Long/Boolean)` — unquoted primitives rendered literally (`true`/`false` lowercase for booleans), so page JS receives real types rather than strings.
## Junior notes
- Parameters are joined with `", "` in insertion order — order must match the page-side function signature exactly; there are no named arguments to save you.
- Never concatenate article HTML into a JS string by hand; always go through this builder or a quote in a saved page will break the reader with a JS syntax error.
