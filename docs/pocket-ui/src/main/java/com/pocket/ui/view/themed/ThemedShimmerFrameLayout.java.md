# pocket-ui/src/main/java/com/pocket/ui/view/themed/ThemedShimmerFrameLayout.java
## What this is
Facebook's ShimmerFrameLayout (a container that sweeps a loading highlight across its children) with Pocket light/dark theme support. For the user it is the shimmering placeholder effect behind skeleton rows; the only addition over the library class is theme-state merging.

## How it fits
AbsSkeletonRow extends this class, so every skeleton row gets the shimmer effect plus automatic re-theming of the placeholder colors underneath. Its only addition is merging the theme attributes from AppThemeUtil in `onCreateDrawableState()`.

## Key pieces
- `onCreateDrawableState()` — merges `AppThemeUtil.getState(this)` into the drawable state; the whole reason the class exists.

## Junior notes
- The shimmer library class is third-party (com.facebook.shimmer); this wrapper is the seam where Pocket theming meets it.
- Shimmer animation start/stop is managed by subclasses (AbsSkeletonRow uses OnlyWhenVisibleHelper), not here.
