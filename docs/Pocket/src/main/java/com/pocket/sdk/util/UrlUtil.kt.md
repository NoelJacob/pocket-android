# Pocket/src/main/java/com/pocket/sdk/util/UrlUtil.kt
## What this is
A tiny URL-equality helper. It answers whether two saved-item URLs point to the same page while ignoring trivial differences like http vs https, a www. prefix, or a trailing slash.
## How it fits
Used wherever the app deduplicates or matches items by URL (e.g. checking if an article is already saved). Callers pass two raw strings to areUrlsTheSame and get a boolean; parsing is delegated to OkHttp's HttpUrl.
## Key pieces
- `areUrlsTheSame(url1, url2)`: normalizes both URLs, parses them, then compares host and each path segment; returns false if either fails to parse.
- `cleanUrl()`: private normalizer forcing an https scheme, stripping www., and trimming slashes before parsing.
## Junior notes
- OkHttp HttpUrl = strict URL parser; toHttpUrlOrNull returns null for malformed input instead of throwing, which is why unparseable URLs safely compare as not-equal. Query parameters are intentionally ignored by the segment-only comparison.
