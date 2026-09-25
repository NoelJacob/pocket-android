# Pocket/src/main/java/com/pocket/sdk/offline/cache/InvalidAssetException.java

## What this is
A checked exception meaning "this URL can never become a valid cache file" (bad characters, unmappable path). It is a permanent per-URL failure, not a retryable network error.

## How it fits
Thrown from `Asset.create(...)` path-mapping when a discovered link cannot be turned into a local file. Callers (markup processors, `WebDownloader`) skip that single asset and continue the page download rather than failing the whole item; the item itself may end up `PARTIAL` if a critical asset was invalid.

## Key pieces
- `InvalidAssetException(String)` — message-only, naming the offending URL/path. WHY: the fix is at the URL-mapping level, so the message must identify the input.

## Junior notes
- Do not retry on this exception — the same URL will fail identically. Retry is for network timeouts; this is a poison URL.
- Catch it narrowly around the single asset, never around the whole page, or one bad image kills an otherwise good download.
