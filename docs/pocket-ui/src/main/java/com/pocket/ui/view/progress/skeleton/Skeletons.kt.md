# pocket-ui/src/main/java/com/pocket/ui/view/progress/skeleton/Skeletons.kt
## What this is
The Jetpack Compose (Android's modern declarative UI toolkit, where screens are functions instead of XML layouts) equivalent of SkeletonView: small grey placeholder shapes shown while content loads. Two functions cover the cases: a generic pill-shaped block and a text-line placeholder sized to match surrounding text.

## How it fits
Compose screens call these while data loads, where a View-based screen would use SkeletonView. `Skeleton` draws a Box filled with the themed grey6 color clipped to a circle/pill shape. `TextSkeleton` wraps it, padding and sizing the block to the current text style's font size so the placeholder sits exactly where the text line will appear. Colors come from PocketTheme, so dark mode works automatically.

## Key pieces
- `Skeleton(modifier, color)` — the base placeholder: a Box with background color in a CircleShape; callers pass size via modifier.
- `TextSkeleton(modifier, style, color)` — sizes the block to `style.fontSize` tall and centers it in `style.lineHeight`, defaulting to the ambient LocalTextStyle so it matches neighboring Text.

## Junior notes
- Compose placeholders are just functions called during composition; there is no view inflation or Binder here.
- `LocalDensity.current` converts the text style's sp sizes to dp for the padding/height math.
