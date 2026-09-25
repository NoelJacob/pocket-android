# pocket-ui/src/main/java/com/pocket/ui/view/item/ItemVideoIndicatorDrawable.java
## What this is
The circular "play" badge drawn on top of thumbnails to tell the user "this saved item is a video, not an article": a semi-transparent dark circle with a white play triangle centered in it. It deliberately ignores the app theme (always the same grey/white) so the badge looks identical on every surface.
## How it fits
Never used directly by screens — `ItemThumbnailView.setVideoIndicatorStyle()` picks one of the three factories (`forItemRow`, `forItemTile`, `forDiscoverTile`) and `ItemThumbnailView.onDraw()` centers it over the thumbnail. List rows use the 17dp row badge (`ic_pkt_play_mini`); tiles and Discover surfaces use larger variants (`ic_pkt_play_mini` at 24dp, `ic_pkt_play_solid` at 36dp). The class comment explains why it is hand-drawn instead of an XML `layer-list`: vector-compat layer-lists would require a heavy global AppCompat flag.
## Key pieces
- `forItemRow()` / `forItemTile()` / `forDiscoverTile()` — the only constructors callers use, each pairing an icon asset with a circle radius. WHY: badge size is a property of the surface (row vs. tile vs. Discover), so callers pick a surface instead of hand-tuning pixels.
- `onBoundsChange()` — re-centers the inner play icon whenever the thumbnail bounds change. WHY: the drawable is stretched to the full thumbnail rect at draw time, so the icon must re-anchor to the center on every size change.
- `draw()` — paints the inherited `circlePaint` circle then the white icon on top. WHY: two layers (dim circle for contrast + white glyph) keep the badge readable over any photo.
- `getIntrinsicWidth/Height()` — report `circleRadius * 2`. WHY: tells the parent how big the badge wants to be independent of the thumbnail it overlays.
## Junior notes
- Colors are fixed (`pkt_grey_2` at 80% alpha, white icon) and never themed — do not "fix" this to use themed colors; consistency across thumbnails is intentional.
- The icon is a `VectorDrawableCompat` tinted white at construction; swapping `R.drawable.ic_pkt_play_mini` for a non-vector asset without updating the tint path will lose the white tint.
