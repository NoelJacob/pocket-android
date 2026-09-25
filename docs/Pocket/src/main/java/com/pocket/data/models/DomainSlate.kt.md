# Pocket/src/main/java/com/pocket/data/models/DomainSlate.kt
## What this is
One row (a "slate": a themed shelf like "Today's top stories") in the Home feed: an optional title and subheadline plus its list of `DomainRecommendation` cards.
## How it fits
Built by `HomeRepository.toDomainSlate()` from each server `CorpusSlate`. The Home screen renders one section per slate and fills it with the slate's recommendations.
## Key pieces
- `DomainSlate` — `title`/`subheadline` are nullable because some slates are untitled; `recommendations` is never null, just empty when the slate has no cards.
## Junior notes
- Title being null is normal, not an error; render the cards without a header in that case.
- An empty `recommendations` list means the row should be hidden, not shown as an empty shelf.
