# pocket-ui/src/main/java/com/pocket/ui/view/visualmargin/VisualMarginConstraintLayout.java
## What this is
A ConstraintLayout that spaces views by what the eye sees instead of by box edges. Designers specify the exact visual gap (e.g. 16dp between two text blocks) and the layout subtracts invisible font padding automatically, so screens come out pixel-perfect without hand-tweaked margins. It extends ThemedConstraintLayout, so it is theme-aware too.

## How it fits
Screens and rows needing exact vertical rhythm (SettingsSwitchView extends it) use `app:visualMargin_top` instead of `layout_marginTop` on the lower view of a `layout_constraintTop_toBottomOf` chain. During `onMeasure` it resolves the anchor above (following through GONE views to the next visible one), asks both views for their visual insets via the VisualMargin interface, and rewrites topMargin to visualMargin minus those insets, then re-measures. `visualMargin_goneTop` overrides the gap when every anchor above is gone. Only `layout_constraintTop_toBottomOf` chains are supported.

## Key pieces
- `onMeasure()` — two passes: prepare anchors, measure once for positions, rewrite each visual margin to max(0, target minus insets), and re-measure if anything changed.
- `resolveTopAnchorOf()` — follows topToBottom links up through GONE views to the effective anchor.
- `calculateAscentOf()` / `calculateDescentOf()` — visual insets of the child and anchor, zero for non-VisualMargin or non-visible views.
- `prepareAscent()` / `prepareDescent()` — lets anchors shed conflicting padding before measurement.
- Inner `LayoutParams` — adds `visualMarginTop` and optional `visualMarginGoneTop` attributes, seeding topMargin with the visual value for a close first pass.

## Junior notes
- Only Top_toBottomOf constraints participate; visual margins on other anchor types are ignored.
- ConstraintLayout forbids negative margins, so overlapping-tight gaps clamp at zero via Math.max; designs needing overlap must use another mechanism.
