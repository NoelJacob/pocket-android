# Pocket/src/main/java/com/pocket/repository/SearchRepository.kt
## What this is
Persists the user's recent search strings (the "recent searches" chips above results). Actual item searching happens elsewhere; this repo only records and replays what the user typed.
## How it fits
Used by the search screen: `getRecentSearches()` drives the suggestion chips as a Flow (observable stream) from the local cache, and `addRecentSearch(text)` queues a `recent_search` sync action so the chips follow the account across devices. The giant class KDoc above it documents the server `get` search API (states, sorts, paging, operators like `OR`/`tag:`) as background reading, not behavior of this class.
## Key pieces
- `getRecentSearches()` — `bindLocalAsFlow()` on the `recentSearches` thing; WHY a Flow: chips update live when a sync adds a term.
- `addRecentSearch(text)` — fire-and-forget `pocket.sync(null, recent_search...)` with `Timestamp.now()`; WHY fire-and-forget: typing shouldn't wait on the network.
## Junior notes
- Free search matches only title/URL; full-text (article body, tags, authors) is a Premium feature, which is why results differ by account.
- Don't read the example URLs in the KDoc as live credentials; they embed a QA test account's keys and are documentation samples only.
