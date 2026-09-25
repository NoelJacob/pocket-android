# Pocket/src/main/java/com/pocket/sdk/premium/PermanentLibraryUtil.java
## What this is
Builds a signed Permanent Library URL that a WebView can load. It takes an item's URL or id, stamps the current time and the user's id, hashes them with a secret salt, and appends everything as query parameters.
## How it fits
Called by premium article-display code (the permanent-copy viewer) when the user opens a saved page. It reads the user id from `App.pktcache()` and the base URL from `PocketServer.PERM_LIBRARY`, and produces a `Uri` the WebView loads downstream.
## Key pieces
- `getLibraryWebViewUrl / getLibraryWebViewUri(item)` — convenience overloads that pull the URL and id out of a sync `Item` (a saved article record).
- `getLibraryWebViewUri(givenUrl, itemId)` — core builder: picks `pl_i` (item id) or `pl_gu` (raw URL) parameter, adds `pl_h` hash, `pl_u` user, `pl_t` timestamp, and `fallback_url`.
- `hash(timestamp, uid, itemIdentifier)` — creates the SHA256 signature (`ByteString.sha256().hex()`, a hash helper from the Okio library) the server checks to prove the link is genuine.
## Junior notes
- Only valid for premium users; for anyone else the server rejects the URL, so callers must gate on premium status first.
- `URLEncoder.encode` uses form encoding (spaces become `+`); that is intentional here since the value is nested inside another query parameter.
