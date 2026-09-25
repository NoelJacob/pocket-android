# pocket-ui/src/main/java/com/pocket/ui/view/info/FeedFooterView.kt
## What this is
The "you've reached the end" footer at the bottom of a feed list: a short message plus a small Lottie animation (Lottie = a library that plays vector animations exported from After Effects). It is a `ThemedConstraintLayout` (a `ConstraintLayout` that re-resolves its colors when the app switches light/dark theme) inflating `R.layout.view_feed_footer`.
## How it fits
Hosted as the last row/footer of feed RecyclerViews (lists of saved items), downstream of whatever list adapter owns the feed. The list calls `binder.text(...)` to set the end-of-feed message and `playAnimation()` to run the animation (e.g. a loading spinner or celebratory graphic) when the footer scrolls into view.
## Key pieces
- `binder` (public `val`) — exposes `text(CharSequence|StringRes)` and `clear()` (empties the text). WHY: matches the pocket-ui convention where every custom view is configured through a small `Binder` instead of exposing child views.
- `text: TextView` / `animation: LottieAnimationView` — the two inflated children (`R.id.text`, `R.id.animation`). WHY: message plus motion in one reusable footer so each feed screen does not rebuild it.
- `playAnimation()` — starts the Lottie animation. WHY: separated from binding because animation should only run when the footer is actually visible, not at bind time during off-screen recycling.
## Junior notes
- `R.layout.view_feed_footer` must contain views with ids `text` and `animation`, or `findViewById` returns null and the constructor crashes — if you edit that layout, keep those ids.
- Lottie views keep animating while attached; in a scrolling list only call `playAnimation()` when the footer is on screen, otherwise you waste battery animating off-screen frames.
