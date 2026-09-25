# Pocket/src/main/java/com/pocket/data/models/CorpusRecommendation.kt
## What this is
A thin wrapper pairing a recommendation id with its `CorpusItem` content. The id identifies this particular recommendation event; the item holds the article card data.
## How it fits
Built by `toDomainCorpusRecommendation()` from the generated `thing.CorpusRecommendation`. Produced in lists by `RecommendationsRepository.getEndOfArticleRecommendationsFlow()` and shown as "related after article" cards in the reader.
## Key pieces
- `CorpusRecommendation` — `id` plus `corpusItem`; keeps recommendation identity separate from article content.
- `toDomainCorpusRecommendation()` — maps both nested objects in one step so callers get a fully domain-typed pair.
## Junior notes
- Both `id?.id!!` and `corpusItem?...!!` assert non-null; a malformed server response crashes here rather than rendering a blank card.
- Don't confuse this with `DomainRecommendation` (the Home slate card); this type is only for the end-of-article rail.
