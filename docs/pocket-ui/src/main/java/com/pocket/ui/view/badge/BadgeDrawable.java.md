# pocket-ui/src/main/java/com/pocket/ui/view/badge/BadgeDrawable.java

## What this is
A small rounded-rectangle background drawable used behind text badges. It paints a filled round rect with a 4dp corner radius, swapping to a grey disabled color when the view is disabled. It is package-private, so only other badge classes in this package can use it.

## How it fits
`TextBadgeView.setBadgeColor()` wraps the caller's color list in a new `BadgeDrawable` and installs it via `setBackgroundDrawable()`. `TextBadgeView` owns the foreground text; `BadgeDrawable` owns only the pill background. State changes (enabled/disabled/pressed) flow in from the host `TextView`, and `updateDrawComponents()` picks the matching fill color.

## Key pieces
- `BadgeDrawable(Context, ColorStateList)`: stores the normal fill colors plus the `pkt_badge_disabled` fallback; sets up the fill `Paint` (anti-aliased, dithered, fill style).
- `updateDrawComponents()`: resolves the fill color for the current drawable state, forcing the disabled grey when `state_enabled` is absent; calls `invalidateSelf()` when the color actually changed (works around a TextView quirk where unchanged text color skips invalidation).
- `onBoundsChange(Rect)`: re-syncs the internal `RectF` to the new bounds so the round rect always fills the view.
- `draw(Canvas)`: single `drawRoundRect` call with the stored corner radius.

## Junior notes
- A `Drawable` that changes appearance with view state (pressed, disabled) must return `true` from `isStateful()` and do its work in `onStateChange()`; otherwise state never reaches it.
- `getColorForState(state, default)` picks the matching entry from a color list XML; the second argument is the fallback when nothing matches.
