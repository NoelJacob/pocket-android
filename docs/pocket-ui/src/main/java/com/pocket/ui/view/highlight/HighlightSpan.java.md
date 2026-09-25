# pocket-ui/src/main/java/com/pocket/ui/view/highlight/HighlightSpan.java

## What this is
A text span (a markup object attached to a character range that changes how that range draws) that paints a padded amber-ish background behind search matches or highlighted quotes. Unlike the stock BackgroundColorSpan (which only recolors the tight glyph box), this one draws a slightly larger rectangle with side padding and controlled ascent/descent, and it correctly handles highlights that wrap across lines by measuring exactly the visible slice of each line.

## How it fits
The drawing engine behind HighlightTextView (which auto-covers its whole text) and find-in-page highlighting: callers attach one HighlightSpan over the full text plus HighlightedRegion markers over each match via `attach()`, and Android calls `drawBackground()` per line. Colors come from a ColorStateList (a color that varies with view state like pressed/disabled) resolved against the host view's drawable state each draw. `removeAll()` strips both span types to reset.

## Key pieces
- `attach(spannable, startInclusive, endExclusive)`: the setup convenience; stamps a HighlightedRegion over the match range and this span over the whole text, which is WHY the span must cover everything even though only regions paint.
- `drawBackground(...)`: per-line paint; skips regions outside the line, computes left/right edges by measuring text up to the region bounds (so wrapped lines highlight only their slice), insets by `sidePadding`, derives top/bottom from font metrics plus `ascent`/`descent` padding, and fills the rect.
- `HighlightedRegion`: empty marker span flagging which ranges paint; multiple regions per span are supported (multiple matches).
- `removeAll(text)`: removes every HighlightSpan and HighlightedRegion so re-highlighting never stacks stale rectangles.
- Constructors: full version takes side padding, ascent/descent padding, color list, a StateSource (gives current drawable state for color resolution), and a MetricsSource (gives current font metrics); the convenience version derives sensible dp defaults and live view metrics from a TextView.
- `StateSource` / `MetricsSource`: tiny indirection interfaces so the span reads fresh state/metrics at draw time instead of caching stale values.

## Junior notes
- A span MUST cover the whole text while regions mark matches; attaching the HighlightSpan to only the match range breaks multi-line measurement.
- `drawBackground()` runs during text layout/draw (hot path): it allocates nothing per call except using the reused `rect`, so keep it that way.
- `LeadingMarginSpan.Standard` (the parent, normally for bullet margins) is inherited for its margin/measure behavior; the visible highlight comes from the `LineBackgroundSpan` half.
