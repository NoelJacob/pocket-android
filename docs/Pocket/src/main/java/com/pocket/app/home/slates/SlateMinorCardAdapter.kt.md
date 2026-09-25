# Pocket/src/main/java/com/pocket/app/home/slates/SlateMinorCardAdapter.kt
## What this is
This is the adapter for the small horizontally-scrolling (phone) or grid (tablet) story cards inside one slate — every story except the first, which gets the big hero card. It is a plain `RecyclerView.Adapter` (not a `ListAdapter`): data is pushed via `setData()` and the whole row set is refreshed with `notifyDataSetChanged()`.
## How it fits
Each `SlatesAdapter` view holder (phone or tablet) owns one instance of this adapter for its nested `minorCardRecyclerView`. The parent passes `recommendations.drop(1)` (everything after the hero) plus the slate title via `setData()`. Row binding delegates entirely to `DefaultSlateViewHolderHelper.bind()`, so taps/saves/overflow behave exactly like the hero card. `itemWidth` (phone only) fixes each card's width for the horizontal scroll strip.
## Key pieces
- `setData(recommendations, slateTitle)` — stores both fields and calls `notifyDataSetChanged()`; simple but loses item-change animations.
- `MinorCardViewHolder.bind(state)` — one-line delegation to the shared helper with the row's bound widgets.
- `init { itemWidth?.let ... }` — sets card width once per holder; null on tablets where the grid sizes cards.
## Junior notes
- `notifyDataSetChanged()` redraws every minor card even if one save-badge changed — acceptable here because slates are small, but don't copy this for long lists; use `ListAdapter` + `DiffUtil` like the outer adapters.
- `slateTitle` is `lateinit` and must be set via `setData` before any bind; binding before data would crash — in practice the parent always calls `setData` during its own bind first.

