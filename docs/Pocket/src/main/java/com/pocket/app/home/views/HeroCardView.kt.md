# Pocket/src/main/java/com/pocket/app/home/views/HeroCardView.kt
## What this is
This is the big hero card container used for the first story in each phone slate — a `ThemedCardView` (theme-aware Material card from Pocket's UI library) that inflates `ViewHomeSlateHeroCardBinding` and exposes it as `binding` so adapters can fill in title, image, save button, and overflow icon.
## How it fits
Used inside `ViewHomeSlateDefaultBinding` (the phone slate row layout) as `binding.heroCard`; `SlatesAdapter.SlateViewHolder` reaches the inner card widgets via `binding.heroCard.binding` and hands them to `DefaultSlateViewHolderHelper.bind()`. The details screen reuses the same card layout through `DetailsAdapter`'s identical `binding.root.binding` path.
## Key pieces
- `binding` (public val) — inflated with `LayoutInflater.from(context)` and attached to `this`, with `MATCH_PARENT` width, `WRAP_CONTENT` height, 16dp corner radius, and 1dp top margin (`@Suppress("MagicNumber")` acknowledges the literals).
- `setupAnalytics()` — currently empty; a placeholder hook where hero-card impression wiring would go.
## Junior notes
- This is a plain `View` subclass placed in XML (constructor takes `Context` + `AttributeSet`), not a Fragment or Compose UI — it has no lifecycle of its own.
- Corner radius is set in code (`DimenUtil.dpToPx`), not in XML — if cards look square after a theme change, look here, not at the layout file.

