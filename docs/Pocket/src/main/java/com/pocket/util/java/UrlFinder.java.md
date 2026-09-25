# Pocket/src/main/java/com/pocket/util/java/UrlFinder.java
## What this is
Extracts URLs from free text using Android's `Patterns.WEB_URL` matcher, then keeps only entries `URLUtil.isValidUrl()` accepts. `getUrlsFromText()` returns all (or up to a limit), and `getFirstUrlOrNull()` returns just the first hit.
For example, pasting "check this out https://example.com/a and this" into the add-URL field yields `["https://example.com/a"]` via `getFirstUrlOrNull()`.

## How it fits
Called on the share/save entry paths: `IntentItemUtil` pulls candidate URLs from an incoming share `Intent`'s text, and `AddUrlBottomSheetViewModel.onSaveButtonClick()` extracts the first URL from the typed field before invoking the `Save` use case. Null input yields null; no matches yields an empty list or null for the first-URL variant.

## Key pieces
- `getUrlsFromText(text)` / `getUrlsFromText(text, limit)`: scan with a cached `Patterns.WEB_URL`, validate each hit. WHY they exist: turn arbitrary pasted/shared text into saveable links, with `limit` bounding work.
- `getFirstUrlOrNull(text)`: limit-1 convenience returning the first valid URL or null. WHY it exists: most entry points only need one link.
- `mPattern` (cached pattern): WHY it exists: compiling the regex once avoids recompiling on every share or keystroke-adjacent call.

## Junior notes
- Any throwable during matching returns null, so callers must null-check; an empty result and a failure look different (empty list vs null).
- `Patterns.WEB_URL` is heuristic and `isValidUrl` is lenient; downstream save/parse code must still handle unreachable or malformed URLs.
