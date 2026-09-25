# pocket-ui/src/main/java/com/pocket/ui/view/visualmargin/VisualMargin.java
## What this is
A small contract for views whose visible content does not fill their bounding box, most notably text: a TextView's box includes font ascent space above and descent space below the actual glyphs. Views implementing this report those invisible insets so a parent can space content by what the eye sees rather than by box edges.

## How it fits
VisualMarginConstraintLayout is the consumer: during measurement it asks the views above and below a gap for `visualDescent()` and `visualAscent()` and subtracts them from the requested visual margin. ThemedTextView is the main implementation, computing the values from font metrics. The prepare methods let a view shed conflicting default padding when used as a margin anchor; most views leave them as no-ops. The `removeTopMargin` helper zeroes a view's top margin (including the visual-margin field).

## Key pieces
- `visualAscent()` — pixels between the view's top edge and its visible content top.
- `visualDescent()` — pixels between the view's bottom edge and its visible content bottom.
- `prepareVisualAscent()` / `prepareVisualDescent()` — optional cleanup of the view's own padding when serving as bottom/top anchor; returns true if layout changed.
- `removeTopMargin(view)` — static helper that zeroes topMargin (and the LayoutParams visualMarginTop) when present.

## Junior notes
- Only VISIBLE views contribute insets; GONE views are skipped and INVISIBLE anchors still count, matching how the parent resolves chains.
- If a custom view has intrinsic padding that should not count toward spacing, implement this interface and report it.
