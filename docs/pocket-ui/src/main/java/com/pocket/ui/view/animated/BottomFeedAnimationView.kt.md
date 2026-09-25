# pocket-ui/src/main/java/com/pocket/ui/view/animated/BottomFeedAnimationView.kt

## What this is
The little book animation played once at the bottom of the feed: a Lottie (a library that renders After Effects animations exported as JSON) book-flipping clip from `pkt_feed_footer_anim.json` whose page-outline stroke is recolored black in light mode and white in dark mode. The `hasPlayed` flag makes it strictly play-once — calling `playAnimation()` again after the first play is a no-op, so scrolling past it or rebinding the footer never restarts the clip.

## How it fits
It extends `ThemedLottieAnimationView`, whose constructor auto-loads `asset()` and whose `drawableStateChanged()` applies `light()` or `dark()` color overrides whenever the Pocket theme flips. This subclass supplies the three answers: which JSON file (`asset()`), and which `KeyPath` (the address of a layer inside the Lottie file, here the `PAGE_COLOR` stroke of the dynamic book layer) gets which stroke color per theme. The feed's footer container hosts it like any Lottie view and calls `playAnimation()` when it scrolls into view.

## Key pieces
- `asset()`: returns `"pkt_feed_footer_anim.json"` — WHY a function and not a constant is that the base class calls it in its `init` block to load the animation.
- `light()` / `dark()`: each returns one `ColorChange` mapping the same page-stroke keypath to `STROKE_COLOR` in black (`R.color.black`) or white (`R.color.white`) — WHY both override the identical keypath is that a single JSON ships for both themes and only the stroke color differs.
- `playAnimation()`: guards with `hasPlayed` so only the first call reaches `super.playAnimation()` — WHY it exists is to prevent replays on scroll/rebind.
- `ColorChange` (inherited data class): bundles keypath + Lottie property + color; the base class applies each via `addValueCallback()`.

## Junior notes
- `ThemedLottieAnimationView` re-themes in `drawableStateChanged()`, which the framework calls on theme/state changes — if you add a new recolorable layer, add its keypath to both `light()` and `dark()`, or it will keep the baked-in JSON color in one theme.
- Finding keypaths is the hard part: the base class has a `debugKeypaths()` hook that logs every keypath when overridden to `true` — use that rather than guessing the `"Book Animation - DYNAMIC"` path segments.
