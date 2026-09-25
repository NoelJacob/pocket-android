# Pocket/src/main/java/com/pocket/app/reader/internal/article/recommendations/RecommendationSpacingDecorator.kt
## What this is
Adds breathing room around each end-of-article recommendation card. It is a `RecyclerView.ItemDecoration` (a hook that offsets or draws around list rows without touching the adapter) that insets every card 18dp on the left, right, and bottom.
## How it fits
Attached to the recommendations RecyclerView next to `EndOfArticleRecommendationsAdapter`. The adapter worries about content; this class worries purely about spacing, so margins stay consistent even when the card layout changes.
## Key pieces
- `margin: Float = 18f` — the single knob, in dp, converted to pixels per-device via `DimenUtil.dpToPxInt`.
- `getItemOffsets(...)` — writes the left/right/bottom insets into `outRect` for each row; no top inset, so the first card sits flush under the section header.
## Junior notes
- `ItemDecoration` offsets apply per-item at layout time — cheaper and cleaner than padding inside each card layout, which would also pad dividers or backgrounds.
- dp (density-independent pixels) scale with screen density; always convert with a helper like `DimenUtil` rather than hardcoding pixels.
