# Pocket/src/main/java/com/pocket/data/models/CorpusItem.kt
## What this is
A flat display model for one editorially curated candidate (a "corpus item": content Pocket's editors or algorithms nominated for the Home feed). It holds only what a card needs: title, publisher, excerpt, image, URL, and whether the user already saved it.
## How it fits
Built by `toDomainCorpusItem()` from the generated sync-engine `thing.CorpusItem`, wrapping a `CorpusRecommendation`. Consumed by recommendation rows such as the end-of-article list from `RecommendationsRepository`.
## Key pieces
- `CorpusItem` — the card data; `url` is the article link, `isSaved` drives the saved-state icon.
- `toDomainCorpusItem()` — pulls display fields out of the nested `preview` object (`_title()`, `_domain()`, `_excerpt()`, `_image()`, `_url()`); WHY: hides the awkward generated accessors from UI code.
- `isSaved` (private val) — true when the linked `savedItem` has status `ARCHIVED` or `UNREAD`; centralizes the "does the server think I saved this?" check.
## Junior notes
- `preview!!._url()!!` uses non-null assertions (`!!` crashes on null), so a corpus item without a preview URL will crash; that invariant comes from the server.
- `orEmpty()` on the other fields means missing title/publisher/excerpt degrade to blank text instead of crashing.
