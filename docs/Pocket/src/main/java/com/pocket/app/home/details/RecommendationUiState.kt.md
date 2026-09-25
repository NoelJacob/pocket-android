# Pocket/src/main/java/com/pocket/app/home/details/RecommendationUiState.kt
## What this is
This is the plain display model for one story card on Home and the details screens. It flattens everything a card needs (title, publisher, read time text, image URL, saved badge, excerpt, plus analytics IDs) into a single immutable data class, so adapters and card views never touch network or database types directly.
## How it fits
Producers: `SlateDetailsViewModel` maps `DomainRecommendation` items via `toRecommendationUiState()`, and `TopicDetailsViewModel` maps server `Item` objects via its overload. Consumers: `DetailsAdapter`, `SlatesAdapter` (through `DefaultSlateViewHolderHelper`), and the hero/minor card views read these fields to fill in card widgets.
## Key pieces
- `RecommendationUiState` — the card model; `index` records the story's position for analytics, `corpusRecommendationId` identifies which recommendation slot produced it.
- `DomainRecommendation.toRecommendationUiState(stringLoader)` — maps Home lineup data; note the `"View Original"` excerpt guard, which works around a backend bug that sends placeholder text as the excerpt.
- `Item.toRecommendationUiState(stringLoader, index)` — maps topic-feed items; derives `isSaved` from `ItemStatus.UNREAD/ARCHIVED` and `isCollection` from a non-null collection slug.
- `timeToReadText(stringLoader, duration)` — rounds seconds to the nearest minute (30s rounds up) and formats via the `nm_time_to_read_estimate` plural string, so "1 min" vs "5 mins" is handled by Android resources.
## Junior notes
- `StringLoader` is an injectable wrapper around Android string/plural resources so formatting logic is unit-testable without a `Context`.
- Both mappers take `stringLoader` as a parameter rather than injecting it — this file has no Hilt scope, it's just pure functions, which is why ViewModels pass their own instance in.
- This class is also reused by the main Home slate cards, not just details screens — changing a field affects both.

