# Pocket/src/main/java/com/pocket/data/models/DomainItemUtil.kt
## What this is
Helper properties that answer "how long will this take the user?" for a `DomainItem`. Reading time is estimated from word count; video time comes from the video length.
## How it fits
Used wherever the UI shows a time estimate, e.g. `DomainRecommendation.viewingTime` on Home cards. It layers on top of `DomainItem` without changing the model.
## Key pieces
- `DomainItem.isVideo` — true when `type == ItemType.VIDEO`; WHY: callers shouldn't repeat the enum comparison.
- `DomainItem.articlePosition` — the `ARTICLE`-type entry from `positions`, i.e. where the user left off reading; null when never opened.
- `DomainItem.viewingTime` — a `Duration` (a time-span value) for display; null when it can't be estimated.
- `viewingSeconds` (private) — articles need `isViewed` plus a word count above `ItemUtil.minWordCountForViewingTime()`; videos use the first video's length; otherwise null.
## Junior notes
- Reading time assumes 220 words per minute; it intentionally returns null for very short pieces instead of showing "0 min".
- The unused `Context`/`R` imports are dead weight; `isViewed` gating means unopened articles show no estimate.
