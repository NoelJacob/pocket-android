# Pocket/src/test/java/com/pocket/app/sdk2/view/ModelBindingHelperTest.kt
## What this is
Tests for `ModelBindingHelper`, which formats list-row titles and domains, including search-highlight behavior. It proves plain titles/domains pass through, local vs server highlights render correctly with and without matches, and null titles/domains fall back safely.
## How it fits
Guards production `com.pocket.sdk2.view.ModelBindingHelper`, used by `MyListViewModel` and `RecentSavesViewModel` to bind `Item` models to rows. `StringLoader` (Android string lookup) is the only mock; items are real `Item.Builder` fixtures.
## Key pieces
- `setup()` plus `testItem` (title/domain.com) — shared fixture; WHY: every case varies search state around the same item.
- Title tests (not-searching, local-highlights with/without match, server highlights with/without match, null title) — verify title formatting per search mode; WHY: three highlight sources (none, local, server) each render differently.
- Domain tests (not-searching, local-highlight domain/URL match, server-match with/without domain hit, null domain) — verify domain/URL fallback chain; WHY: search matches may hit the URL even when the domain text does not match.
## Junior notes
- Local highlights means client-side substring matching; server highlights come precomputed on the item — the helper prefers whichever the mode selects.
- Null-safety cases exist because server items can omit title/domain; the helper must never crash the row bind.
