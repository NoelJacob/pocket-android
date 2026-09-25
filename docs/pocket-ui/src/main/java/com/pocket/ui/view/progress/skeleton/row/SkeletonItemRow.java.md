# pocket-ui/src/main/java/com/pocket/ui/view/progress/skeleton/row/SkeletonItemRow.java
## What this is
One concrete shimmering placeholder row shaped like a Pocket list item: a thumbnail block plus text-line bars. It exists so the loading list looks like the article list that is about to appear.

## How it fits
SkeletonList's adapter creates 20 of these as its placeholder rows. All behavior (shimmer setup, visibility gating) comes from AbsSkeletonRow; this class only picks the layout by returning `R.layout.view_skeleton_item_row` from `getLayout()`.

## Key pieces
- `getLayout()` — returns the item-row skeleton layout; the single reason this subclass exists.

## Junior notes
- New skeleton row shapes mean adding a layout plus a tiny subclass like this one, not touching the shimmer logic.
