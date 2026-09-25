# pocket-ui/src/main/java/com/pocket/ui/view/themed/ThemedViewPager.java
## What this is
A ViewPager (a stock swipe-between-pages container) with Pocket light/dark theme support plus a fix so WRAP_CONTENT height works. For the user it is normal horizontal paging; what it adds over stock is theme-state merging and measuring its height to the tallest child.

## How it fits
Onboarding and paged content screens use this where pages have different heights. `onCreateDrawableState()` merges the theme attributes like all Themed views. `onMeasure()` works around the stock bug where ViewPager ignores WRAP_CONTENT height: it measures each child unconstrained and forces its own height to the tallest one.

## Key pieces
- `onCreateDrawableState()` — merges `AppThemeUtil.getState(this)` into the drawable state for theme-aware page backgrounds.
- `onMeasure()` — when layout height is WRAP_CONTENT, measures every child with UNSPECIFIED height and adopts the max; otherwise defers to stock measuring.

## Junior notes
- The height fix measures all children on every pass, so keep page counts small or pages cheap to measure.
- This is the legacy ViewPager, not ViewPager2; new paged screens should check whether ViewPager2 (or Compose pager) fits before reusing this.
