# Pocket/src/main/java/com/pocket/app/home/slates/DefaultSlateViewHolderHelper.kt
## What this is
This is a shared binding helper (a Kotlin `object`, i.e. a singleton) that fills in a slate story card — hero or minor — from a `RecommendationUiState`. The hero card and the small horizontal cards look almost identical, so this one `bind()` function prevents the same title/image/save/overflow wiring from being copy-pasted in every adapter.
## How it fits
Called from `SlatesAdapter`'s phone/tablet view holders (hero card = first story) and from `SlateMinorCardAdapter` (the rest). It takes the already-inflated widgets (title/domain/read-time/image/collection/save/overflow views, plus optional excerpt) and the `HomeViewModel`, and wires everything: text, lazy thumbnail, save-button state + listener, card tap, and overflow tap.
## Key pieces
- `bind(slateTitle, viewModel, state, ...)` — the single choke point for card rendering; `slateTitle` and `state.index` feed analytics on tap (`onItemClicked(url, slateTitle, positionInSlate, corpusRecommendationId)`).
- Save wiring — `saveLayout.bind().clear().setSaved(...).setOnSaveButtonClickListener {...}` resets recycled state first, then forwards to `viewModel.onSaveClicked`; returns pre-click `isSaved` for the button's optimistic visuals.
- Overflow wiring — forwards to `viewModel.onRecommendationOverflowClicked(...)`, which opens the recommendation overflow sheet.
- `excerpt` (nullable) — only the tablet wide hero card passes it; phone cards leave it null and the `?.let` skips it.
## Junior notes
- `@Suppress("LongMethod")` acknowledges the 13-parameter signature; adding a 14th widget param is a sign the card layout should be encapsulated instead.
- Because holders recycle, every visual property (visibility of read-time/collection, saved state) is set on every bind — never rely on XML defaults surviving a scroll.
- The `rootView` param is typed `ThemedCardView` while the actual cards are `HeroCardView`/`WideHeroCardView` subclasses — any card subclass works here.

