# Pocket/src/main/java/com/pocket/app/listen/CoverflowItemView.java

## What this is
One card in the Listen coverflow carousel: an article thumbnail that shrinks and fades as it slides away from the center of the screen.

## How it fits
Inflated by `CoverflowAdapter` for every track; `setThumbnail` is called on bind, and the off-center shrink/fade runs automatically from `onLayout` and `offsetLeftAndRight` (called by RecyclerView during scroll), so no controller code is needed.

## Key pieces
- `setThumbnail(drawable)` — WHY: the only input; forwards art into the inner `ItemThumbnailView`.
- `disableAutoHiding` — WHY: the shared thumbnail view normally hides itself when empty, but a carousel card must always occupy space — so the listener is cleared and visibility pinned.
- `updateOffCenterTransform` — WHY: measures distance from the parent's center (clamped to one card width) and maps it to 100→70% alpha and 100→90% scale, producing the coverflow perspective effect.

## Junior notes
- Transform math uses `getX()` (position including scroll offset), not layout params — that is what makes it live-update during flings.
- This view does its own drawing-position work, so keep `bind` cheap; heavy work here janks the whole carousel scroll.
