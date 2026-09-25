# Pocket/src/main/java/com/pocket/app/listen/CoverflowAdapter.java

## What this is
The playlist-art adapter behind the Listen player's sideways-swiping coverflow carousel. It holds the current track list and binds each track's thumbnail (or a letter placeholder) into a `CoverflowItemView`.

## How it fits
Owned by `CoverflowView`, which calls `bind(tracks)` from `ListenState` on every player update. Each `ViewHolder` wraps one `CoverflowItemView`; binding resolves the thumbnail through `LazyAssetBitmap` (background image loading keyed per item) or falls back to a generated placeholder.

## Key pieces
- `bind(tracks)` — WHY: swaps the whole list with `notifyDataSetChanged` but skips work when the list is equal — coverflow pages are few and reorder wholesale.
- `ViewHolder.bind(track)` — WHY: picks remote thumbnail vs `PlaceHolderBuilder` letter tile from the track's title initial when art is missing.
- `onCreateViewHolder` — WHY: builds the item view in code with wrap-content width and full height, which is what the snap helper pages across.

## Junior notes
- `notifyDataSetChanged` (instead of a diff) is fine here: the carousel is short and items are images, so per-item animation would look worse, not better.
- The TODO about caching placeholders is real: a new drawable is built per bind today, acceptable only because binds are rare (track changes, not scrolls).
