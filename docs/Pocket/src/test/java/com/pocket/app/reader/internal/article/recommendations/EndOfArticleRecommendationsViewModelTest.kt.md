# Pocket/src/test/java/com/pocket/app/reader/internal/article/recommendations/EndOfArticleRecommendationsViewModelTest.kt
## What this is
Single-behavior test for `EndOfArticleRecommendationsViewModel`, the "more to read" cards at the article's end. It proves tapping a card sends an `endOfArticleContentOpen` event with the URL and recommendation id.
## How it fits
Guards production `EndOfArticleRecommendationsViewModel`, shown inside the article reader and backed by `RecommendationsRepository`, `ItemRepository`, and the `Save` use case. Only the tap-to-analytics path is verified.
## Key pieces
- `setup()` — builds the ViewModel from relaxed mocks; WHY: card taps need no real recommendation data.
- `WHEN a card is clicked THEN we track analytics` — calls `onCardClicked("url", "recId")`, verifies `contentOpenTracker.track(endOfArticleContentOpen(...))`; WHY: recommendation engagement reporting.
## Junior notes
- Both URL and recId must be forwarded — dropping recId would break recommendation attribution downstream.
- Saving from these cards is a separate path and is not covered here.
