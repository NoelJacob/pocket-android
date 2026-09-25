# Pocket/src/main/java/com/pocket/app/home/saves/RecentSavesAdapter.kt
## What this is
This is the RecyclerView adapter for Home's "Recent Saves" horizontal row. It renders up to 5 of the user's own saved stories as small cards (`ViewHomeRecentSaveCardBinding`) with title, publisher, read time, thumbnail, a favorite (heart) toggle, and an overflow menu — distinct from recommendation cards, which have a save button instead.
## How it fits
Created by the Home fragment/screen and given the `RecentSavesViewModel`. Its `init` block collects `recentSavesUiState` (scoped to CREATED via `repeatOnCreated`) and submits each emission to the `ListAdapter`. All taps route into the ViewModel: card tap to `onItemClicked` (opens reader), heart to `onFavoriteClicked`, overflow dots to `onSaveOverflowClicked`, which eventually surfaces `RecentSavesOverflowFragment`.
## Key pieces
- `SavesViewHolder.bind(state, position)` — binds title/domain/read-time (hiding read-time when blank), lazy thumbnail, collection label, bold title for unviewed items (`title.setBold`), and the three click listeners.
- `DIFF_CALLBACK` — identity compares the underlying `item` (the server object), contents compare the whole `SaveUiState`; favorite toggles therefore rebind just that card.
## Junior notes
- Bold title = unviewed (`titleIsBold`), computed in the ViewModel from `item.viewed != true` — the adapter only renders it, so look there if bolding looks wrong.
- `favoriteIcon.isChecked` is set on every bind because holders are recycled; forgetting this on a recycled toggle is the classic "heart state jumps around while scrolling" bug.

