# Pocket/src/test/java/com/pocket/sdk/http/sentry/ExcludedTargetsTest.kt
## What this is
Tests for `ExcludedTargets`, which builds a regex of URLs that error-reporting (Sentry) must ignore. It proves high-volume Pocket endpoints (GraphQL, text parser, v3 send/get/fetch) and the image-cache prefix are excluded, while unrelated URLs (example.com, getItemAudio) still match (i.e. are reported).
## How it fits
Guards production `com.pocket.sdk.http.sentry.ExcludedTargets`, whose `toRegex()` feeds the Sentry/HTTP layer's exclusion filter. Pure unit test over `Pattern.matches`; no Android or network needed.
## Key pieces
- `regex` fixture — excludes five exact endpoints plus the image-cache prefix; WHY: reproduces the production exclusion set.
- `excludes*` tests (graph, send, fetch, get, image-cache) — assert `Pattern.matches` is false; WHY: each noisy endpoint stays out of crash reports. Note: `Pattern.matches` requires a full-string match.
- `includesExample / includesGetItemAudio` tests — assert true for non-excluded URLs; WHY: guards against over-broad exclusions swallowing real errors.
## Junior notes
- `Mode.Exact` vs `Mode.Prefix`: exact matches one URL, prefix matches the host subtree — the image-cache case needs prefix because cache URLs embed the target URL.
- `getItemAudio` is deliberately NOT excluded though it looks like `get`; string-prefix carelessness here would be a bug.
