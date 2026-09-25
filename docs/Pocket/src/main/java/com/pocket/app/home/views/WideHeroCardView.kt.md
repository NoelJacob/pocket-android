# Pocket/src/main/java/com/pocket/app/home/views/WideHeroCardView.kt
## What this is
This is the tablet variant of the hero card — the wide first-story card in each slate on large screens, including an excerpt line the phone card lacks. Like `HeroCardView`, it is a `ThemedCardView` exposing its inflated binding for adapters to fill in.
## How it fits
Used inside `ViewHomeSlateWideBinding` as the slate's hero; `SlatesAdapter.SlatesTabletViewHolder` reaches the inner widgets via `binding.heroCard.binding` and passes them — plus `excerpt` — to `DefaultSlateViewHolderHelper.bind()`. Same 16dp radius and margins as the phone hero so the two look like one family.
## Key pieces
- `binding` (public val, `ViewHomeSlateWideHeroCardBinding`) — attached to `this` with `MATCH_PARENT`/`WRAP_CONTENT` params and 16dp radius.
- `setupAnalytics()` — empty placeholder, mirroring `HeroCardView`.
## Junior notes
- Phone vs tablet hero is chosen by layout (`SlatesAdapter` view type from the `isTablet` flag), not by this class — don't add form-factor checks here.
- The excerpt `TextView` only exists in this wide binding; the shared helper takes it as an optional param for exactly this reason.

