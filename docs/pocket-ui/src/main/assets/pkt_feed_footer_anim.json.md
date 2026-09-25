# pocket-ui/src/main/assets/pkt_feed_footer_anim.json

## What this is

A Lottie animation asset (Bodymovin 5.5.10 export, 177×84, ~24fps) of the "Discovery Book" with a dynamic layer plus confetti, played when the user reaches the end of their feed. Lottie JSON describes vector shapes and keyframes that the Lottie player renders natively instead of shipping video.

## How it fits

Loaded by `BottomFeedAnimationView.asset()` (`com.pocket.ui.view.animated`), which is embedded in `res/layout/view_feed_footer.xml` and surfaced through `FeedFooterView` at the bottom of collection lists. When the list shows its footer, this animation celebrates the "caught up" state.
