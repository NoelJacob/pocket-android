# pocket-ui/src/main/java/com/pocket/ui/view/themed/ThemedCardView.kt
## What this is
A CardView (a stock container with rounded corners and a shadow) that follows Pocket's light/dark theme. It looks and behaves exactly like a normal CardView; the only addition is theme awareness for its background color.

## How it fits
Used for card surfaces in XML layouts wherever a themed rounded card is needed. It merges the app theme state via AppThemeUtil in `onCreateDrawableState` like all Themed views. Because CardView draws its background internally instead of using the normal background drawable, it additionally caches the card-background ColorStateList and re-applies the resolved color in `drawableStateChanged()` on every theme switch.

## Key pieces
- `onCreateDrawableState()` — merges the theme attributes into the drawable state so state-list resources re-resolve.
- `drawableStateChanged()` — re-applies the cached card background color for the new state; this is the extra step plain layouts do not need.
- `setCardBackgroundColor()` — caches the ColorStateList before delegating, so later theme changes can re-resolve it.

## Junior notes
- CardView needs the manual re-apply because `setCardBackgroundColor(int)` bakes in a resolved color; only the ColorStateList overload stays theme-aware.
- The base-class chain is: stock CardView plus theme-state merging plus card-color caching.
