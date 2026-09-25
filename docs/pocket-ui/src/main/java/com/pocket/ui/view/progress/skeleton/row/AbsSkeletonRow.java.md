# pocket-ui/src/main/java/com/pocket/ui/view/progress/skeleton/row/AbsSkeletonRow.java
## What this is
The base class for one shimmering placeholder row in a skeleton loading list. It wraps a row layout in Facebook's ShimmerFrameLayout (a container that sweeps a highlight across its children for the familiar loading shimmer) and starts the shimmer only while the row is actually visible on screen.

## How it fits
SkeletonList's adapter creates SkeletonItemRow instances, which extend this class and supply their layout via `getLayout()` (view_skeleton_item_row). `init()` inflates that layout into a themed content container, configures a subtle alpha shimmer (short flash, 2s pause between sweeps), and uses OnlyWhenVisibleHelper so off-screen rows do not burn CPU animating.

## Key pieces
- `getLayout()` — abstract; subclasses return the row layout resource to inflate.
- `init()` — builds the themed content container with side-grid padding, sets the Shimmer (alpha highlight, base 1.0, highlight 0.3), adds the content, and installs the visibility-gated shimmer start/stop.
- `defaultPadding()` — side padding from pkt_side_grid; subclasses can override.

## Junior notes
- Shimmer auto-start is off; OnlyWhenVisibleHelper calls start/stopShimmer based on actual visibility, which matters in long lists.
- It extends ThemedShimmerFrameLayout, so the row background still follows light/dark state under the shimmer.
