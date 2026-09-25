# Pocket/src/main/java/com/pocket/util/android/webkit/JavascriptFunction.java
## What this is
Builds a single JavaScript call string with correctly escaped parameters, then evaluates it in a `WebView`. Chain `value(...)` calls in order, then `execute(webView)`; the builder is pooled and the instance is single-use after execution.
For example, `new JavascriptFunction("video","play").value("108").execute(wv)` runs `video.play('108')` inside the page.

## How it fits
Legacy bridge for driving page JS (e.g. article-view callbacks). It borrows its buffer from `StringBuilders` and posts evaluation through app threads. The reader has since moved to the Kotlin `JavascriptFunction`/`JavascriptFunctions` command-string builders (used by `ArticleViewModel` and `FindTextViewModel`), so new article code should follow that pattern.

## Key pieces
- `JavascriptFunction(functionName)` / `(object, functionName)`: starts `name(` or `object.name(`. WHY they exist: cover global vs method calls with one builder.
- `value(String[, escape])` / `value(int/long/...)` / `value(JsonNode)` / `valueJsonString`: ordered, escaped parameters. WHY they exist: coerce each Java type into safe JS literal syntax.
- `escapeForSingleQuote` / `escapeJsonForString`: quoting and U+2028/2029 fixes. WHY they exist: prevent quotes, backslashes, and JS line-separator characters from breaking or injecting into the call.
- `execute(WebView)`: freezes the builder and evaluates. WHY it exists: the single point that turns the built string into a page call.

## Junior notes
- Single-use after `execute()`: further `value()` calls throw, so build once and execute once per instance.
- Never pass unescaped user text with `escape=false` unless it was already escaped asynchronously; unescaped quotes enable JS injection from hostile content.
- Large strings should be escaped off the UI thread first (per the javadoc), since escaping big HTML payloads on the main thread drops frames.
