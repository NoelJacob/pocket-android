# Pocket/src/main/java/com/pocket/app/add/IntentItemUtil.java
## What this is
Parses an incoming share/save intent into an `IntentItem` (url + title). For save links (ACTION_VIEW) it reads the `url` query parameter; for shares (ACTION_SEND and friends) it scans `EXTRA_TEXT` for URLs with `UrlFinder` and takes the first, with `EXTRA_SUBJECT` as the title. Static-only (private constructor throws).
## How it fits
Called by `AddActivity` (and any future intent entry) before `AddItemFromIntentUtil.add`. It is the trust boundary for exported intents: malformed data is caught and logged, yielding a null-URL item that downstream renders as an "invalid URL" message.
## Key pieces
- `from(intent)`: the single entry; WHY first-URL-wins is share text routinely contains several links and the lead one is the shared content.
- `findUrlsFromIntent`: VIEW branch (query-param parse, guarded by try/catch + `Logs`) vs extras branch (`UrlFinder.getUrlsFromText`, null-safe to empty list).
## Junior notes
- This runs on exported-activity input: never assume extras exist, types match, or the query param is a valid URL; every access here is already defensive, keep it that way.
- `UrlFinder` handles the messy text scanning; do not pre-trim or regex the text before handing it over, or you will break its detection.
