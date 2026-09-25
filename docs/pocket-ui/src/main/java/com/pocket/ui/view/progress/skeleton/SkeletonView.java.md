# pocket-ui/src/main/java/com/pocket/ui/view/progress/skeleton/SkeletonView.java
## What this is
A single grey rounded-corner placeholder bar used as a building block for skeleton loading screens. It stands in for one line of text or one thumbnail while content loads. Its width can be fixed or randomized to a percentage range so paragraphs look natural.

## How it fits
Screens rarely use it alone; SkeletonParagraphView stacks several of them to fake a paragraph, and skeleton row layouts place them where titles and images go. Callers configure it from XML (`randomWidthPercentFloor/Ceil`, `compatBackgroundColor`, `cornerRadius`) or in code via `bind()`: `background(color, radius)`, `randomWidth(floor, ceil)`, `originalWidth()` to reset, `clear()` for defaults.

## Key pieces
- `init()` — reads the XML attributes and applies them through the Binder; defaults to themed grey 6 with full width.
- `onMeasure()` — when a random-width range is set, shrinks the measured width to a random value in that range using RandomSingleton.
- `getRandomWidth()` — maps the floor/ceil percentages to an actual pixel width.
- Inner `Binder` — `background()` swaps in a ColorStateListDrawable (a drawable that follows light/dark state), `randomWidth()` sets the range and re-requests layout.

## Junior notes
- It extends ThemedView, so the placeholder grey follows the theme state without any extra code.
- `randomWidth` triggers `requestLayout()` because changing width needs a new measure pass, not just a redraw.
