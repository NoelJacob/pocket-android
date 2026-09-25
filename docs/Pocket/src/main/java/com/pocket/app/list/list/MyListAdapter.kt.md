# Pocket/src/main/java/com/pocket/app/list/list/MyListAdapter.kt

## What this is
The RecyclerView adapter that renders every row of the Saves/Archive list: title, domain, excerpt, read-time, thumbnail, favorite star, tag/highlight badges, and swipe-to-archive gestures plus bulk-edit checkboxes.

## How it fits
Owned by the My List fragment and fed by `MyListViewModel`: it collects `listState` (the rows) and `sortFilterState` (to detect sort/filter changes). Taps, swipes, badge clicks, and favorite taps all call straight back into `MyListViewModel` (`onItemClicked`, `onItemSwipedRight/Left`, `onTagBadgeClicked`, ...). It drives the row layout `ViewListItemRowBinding` and delegates bulk-edit slide animations to `BulkEditListItemAnimator`.

## Key pieces
- `sortFilterStateHasChanged` flag — WHY: when sort/filter/search changes, the next submit skips the async diff (`notifyDataSetChanged` + scroll to top + fresh differ) so the list swaps instantly instead of animating nonsense moves.
- `listDiffer: AsyncListDiffer` + `DIFF_CALLBACK` — WHY: computes row changes off the main thread; items compared by `item`, contents by full equality.
- `ItemRowViewHolder.bind(state, position)` — WHY: binds all row visuals, including teal search highlights, excerpt visibility, and bold titles; the biggest method because a row has many states.
- Click/swipe wiring in `bind` — WHY: in edit mode the whole row selects for bulk edit; otherwise tap opens the reader, favorite/share/overflow buttons act per item, and swipes archive. Swiping is disabled in edit mode.
- `setupBadges` — WHY: sorts highlight and matching-tag badges first, builds `BadgeView`s, and routes tag-badge taps to filter by that tag.
- `setThumbnail` — WHY: lazy-loads the article image via `LazyAssetBitmap`, hiding the view when there is no thumbnail.

## Junior notes
- `AsyncListDiffer.submitList` is async: after a sort/filter change the code deliberately bypasses it for one frame — do not "simplify" that branch away or the list will flicker.
- `swipeLayout.reset()` in every bind is required because ViewHolders are recycled; a swiped-open row would otherwise reappear open on an unrelated item.
