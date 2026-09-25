# Pocket/src/main/java/com/pocket/app/listen/CoverflowView.java

## What this is
The horizontal snap-to-card carousel of article art at the top of the Listen player. Swiping it changes tracks; the playing track always snaps to center.

## How it fits
Embedded in `ListenPlayerView`, which calls `bind(ListenState)` on every player update and listens for `OnSnappedPositionChangedListener` to issue next/previous/moveTo commands through `Controls` (the text-to-speech playback API). It reports `CxtUi.COVER_FLOW` for analytics so swipes attribute correctly.

## Key pieces
- Constructor wiring — WHY: horizontal `LinearLayoutManager` + `CoverflowAdapter` + `BetterPagerSnapHelper` (a snap helper that pages one card at a time) is the whole carousel behavior.
- `bind(state)` — WHY: pushes the track list into the adapter and scrolls to `state.index` when playback moved on (e.g. auto-advance), keeping art and audio in sync.
- Scroll listener → `snapListener.onSnappedPositionChanged` — WHY: when scrolling settles, the centered card's position becomes a track-change command in `ListenPlayerView`.
- `onSizeChanged` padding — WHY: `(width - height)/2` side padding centers the first/last cards; without it edge cards could never reach center.
- `hasOverlappingRendering() = false` — WHY: works around a rendering bug where the whole view vanished once alpha dropped below 100%.

## Junior notes
- `scrollToPosition` uses `scrollToPositionWithOffset(position, 0)` rather than smooth-scroll: programmatic jumps (track advance) must land instantly, while user swipes animate via the snap helper.
- `findCenterChild` picks whichever child straddles the middle — with fractional scrolls exactly one child always qualifies.
