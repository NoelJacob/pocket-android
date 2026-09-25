# Pocket/src/main/java/com/pocket/repository/RecommendationsRepository.kt
## What this is
Serves the "related after article" rail: a short list (4) of corpus recommendations for the article currently open in the reader.
## How it fits
Called by the reader/end-of-article UI: first `refreshEndOfArticleRecommendations(url)` pulls fresh picks from the server, then `getEndOfArticleRecommendationsFlow(url)` observes the cached picks as a Flow (observable stream). Each URL's picks are pinned in their own session-scoped `Holder` (`"eoa-$url"`), so opening article B doesn't evict article A's picks mid-session.
## Key pieces
- `refreshEndOfArticleRecommendations(url)` — builds the `relatedAfterArticle` query, `remember()`s it in a per-URL session holder, and `syncRemote(...).await()` to fetch; WHY two steps: the Flow below only reflects the local cache, so something must populate it first.
- `getEndOfArticleRecommendationsFlow(url)` — `bindLocalAsFlow()` on the same query, mapped through `toDomainCorpusRecommendation()`; empty list when nothing is cached yet.
- `END_OF_ARTICLE_RECOMMENDATION_COUNT` (4) — how many cards the rail shows.
## Junior notes
- You must call refresh before collecting the Flow on first open, or you'll sit on an empty list until some other refresh fills the cache.
- The holder is session-scoped, not persistent: these picks don't survive an app restart and are re-fetched each session.
