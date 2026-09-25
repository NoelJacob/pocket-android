# Pocket/src/main/java/com/pocket/data/models/DomainRecommendation.kt
## What this is
One card in the Home feed: title, publisher domain, excerpt, image, URL, saved/collection flags, its position in the list, and an optional viewing-time estimate. `corpusId`/`itemId` tie the card back to the server's curated corpus and item graph.
## How it fits
Built by `HomeRepository` (`CorpusRecommendation.toRecommendation()`) from the Home slate lineup. Rendered by the Home screen's slate rows; tapping a card opens the article, and `isSaved` drives the save button state.
## Key pieces
- `DomainRecommendation` — pure display data; `index` records the card's position for analytics (event logging), `viewingTime` is a `Duration` time-span shown as "x min".
- `corpusId` / `itemId` / `url` — identity triple: which recommendation, which underlying item, and which URL to open.
- `isCollection` / `isSaved` — pick the collection badge vs article layout, and the saved icon state.
## Junior notes
- `index` is analytics-only; don't use it as a list key because the lineup order can change between syncs.
- `viewingTime` may be null here (Home currently passes null); the UI must handle "no estimate" gracefully.
