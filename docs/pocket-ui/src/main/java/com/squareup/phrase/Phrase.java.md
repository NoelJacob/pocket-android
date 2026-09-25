# pocket-ui/src/main/java/com/squareup/phrase/Phrase.java
## What this is
A vendored copy of Square's Phrase library (Apache 2.0, copyright Square Inc. 2013): a small fluent API for substituting named `{placeholders}` into user-visible strings. It fills the gap left by positional `String.format`, so translators can reorder placeholders freely. Provenance is the Square open-source Phrase project; the file is kept in-tree under its original package so imports keep working without adding a dependency.

## How it fits
Callers build text via `Phrase.from(pattern or string/pural resource).put("key", value).format()` or push the result straight into a TextView with `into(view)`. Overloads accept a Fragment, View, Context, or Resources plus a string/plural resource id. Key rules: `{lowercase_underscored}` placeholders, `{{` escapes a literal brace, spans (bold/italic from strings.xml) survive substitution, and mismatches fail fast with IllegalArgumentException. This doc covers role and usage only, not the internal token implementation.

## Key pieces
- `from(...)` — entry points: raw CharSequence, string resources, or plural resources with quantity; each parses the pattern up front.
- `put(key, value)` — binds one placeholder (CharSequence or int); throws when the key is absent or the value is null, and invalidates the cached result.
- `putOptional(key, value)` — binds only when the key exists, for shared code paths across string variants.
- `format()` — validates every key was bound, substitutes into a copy preserving spans, and caches the result.
- `into(textView)` — formats and sets the TextView text in one call.

## Junior notes
- Fail-fast is deliberate: missing keys, unknown keys, and null values throw rather than showing half-built text, so every `put` must match the pattern exactly.
- `toString()` returns the raw unexpanded pattern for debugging; always call `format()` (or `into()`) for display text.
