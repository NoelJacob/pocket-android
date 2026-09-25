# Pocket/src/main/java/org/apmem/tools/layouts/FlowLayout.java
## What this is
A third-party wrapping container (ported from the ApmeM android-flowlayout project, Apache 2.0): children lay out left-to-right and wrap to the next line when they run out of room, like words in a paragraph. It also supports vertical mode, spacing, and a max-lines cap.
For example, a row of tag pills flows as `[travel][food][...]` and wraps to a second line instead of clipping.

## How it fits
Base class of `ChipLayout`, which adds the chip text map, adapter, and click handling on top of this measure/layout. `ChipLayout` in turn backs `SuggestedTagsModule` pills and `ChipEditTextInternal`. Style comes from the `FlowLayout` XML attrs (`horizontalSpacing`, `verticalSpacing`, `orientation`, `maxLines`, `layoutCenter`, `debugDraw`).

## Key pieces
- `onMeasure` / `onLayout` (wrapping algorithm): WHY they exist: the core "pack children into lines, then stack lines" behavior every subclass inherits.
- `HORIZONTAL` / `VERTICAL` + `orientation`: layout axis. WHY it exists: same container works for row-wise tag flows and column-wise stacks.
- `setMaxLines(int)`: 0 means unlimited wrap, >0 caps visible lines, `SINGLE_LINE_NO_CAP` (-1) is one endless line. WHY it exists: Pocket's addition to truncate overflow (e.g. suggestion rows).
- `LayoutParams` with per-child spacing + `horizontalSpacing`/`verticalSpacing`: WHY they exist: gutters between pills without padding hacks in each child.
- `layoutCenter` / `debugDraw`: centering and debug paint. WHY they exist: polish alignment and visualize line boxes when tuning wraps.

## Junior notes
- This is vendored third-party code under Apache 2.0 (see the header); keep the license header and prefer upstreaming generic fixes rather than forking behavior.
- `GONE` children are skipped in measure, so toggling visibility reflows the wrap; animate carefully because line breaks shift siblings.
- `maxLines` hides overflowed children from layout; it does not paginate or scroll them, so pair with a scroll container if overflow must stay reachable.
