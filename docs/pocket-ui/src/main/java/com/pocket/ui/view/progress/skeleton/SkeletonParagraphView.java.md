# pocket-ui/src/main/java/com/pocket/ui/view/progress/skeleton/SkeletonParagraphView.java
## What this is
A placeholder block of 1-2 (configurable) grey rounded bars that stands in for a paragraph of text while article content loads. The bar count is picked randomly between minLines and maxLines each time the view is created, so repeated placeholders do not look identical. The last line is shorter and slightly darker, mimicking a real ragged paragraph ending.

## How it fits
Used inside skeleton screens (for example SkeletonItemRow's layout) wherever a text paragraph will appear. XML attributes `minLines` / `maxLines` (styleable SkeletonParagraphView) control the range; `addLine` builds each bar as a SkeletonView with themed grey colors and randomized width on the final line. With zero lines it hides itself to avoid stray padding.

## Key pieces
- `init()` — reads minLines/maxLines (throws if min > max), picks a random total, and adds that many bars; GONE when total is 0.
- `addLine(context, isLast)` — creates one SkeletonView bar at pkt_skeleton_text_height with 5dp margins; the last bar gets a darker grey and 20-70% width, others 70-100%.

## Junior notes
- Widths are randomized at construction time only, so the same instance keeps stable widths across redraws.
- It extends ThemedLinearLayout (vertical), so the bars re-theme on light/dark switch automatically.
