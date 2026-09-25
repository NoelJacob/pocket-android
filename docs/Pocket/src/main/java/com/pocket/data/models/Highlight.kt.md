# Pocket/src/main/java/com/pocket/data/models/Highlight.kt
## What this is
One yellow-highlight excerpt on an article: its id, the quoted text, and a `patch` string describing exactly where in the article it applies. It is `@Serializable` (convertible to/from JSON automatically) because it is passed into the article WebView's JavaScript.
## How it fits
Converted from sync-engine `Annotation` via `toHighlight()`. `HighlightRepository` exposes highlight lists through `DomainItem`; `ItemRepository` writes them via add/delete annotation actions. The reader screen injects them into article HTML/JS.
## Key pieces
- `Highlight` — `id` (`annotation_id` in JSON), `quote`, `patch`, `version` (patch-format version so old highlights stay readable after format changes); implements `NoObfuscation` (excluded from code obfuscation so JSON keys survive release builds).
- `toHighlight()` — maps an `Annotation` to a `Highlight` with non-null assertions; WHY: keeps the `!!` crash risk in one mapping spot instead of scattered through UI code.
## Junior notes
- `patch` is not human text; it's a positional recipe the JS uses to re-attach the highlight even if the article text shifted slightly. Never display or edit it.
- `@SerialName` values (`annotation_id`, `quote`, `patch`, `version`) must match the JavaScript contract exactly; renaming a Kotlin field without its `@SerialName` breaks the reader.
