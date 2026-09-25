# Pocket/src/main/java/com/pocket/app/listen/ListenPlayerView.java

## What this is
The big expanded Listen player card pinned above the playlist: coverflow art carousel, headline/subhead, scrub bar with time labels, speed control, settings gear, and transport buttons.

## How it fits
Created once inside `ListenView` and also used as the header row of `ListenItemAdapter`'s playlist. `ListenView.bind(state)` drives it via `bind(state, shouldShowDegradedView)`; user gestures go out through three tracked `Controls` (text-to-speech playback API) instances — main, coverflow, and scrubber — so playback, swipes, and seeks attribute to the right analytics surface.

## Key pieces
- `bind(state, degraded)` — WHY: renders headline/author/host, coverflow position, scrub progress vs buffering, and toggles the full scrubber vs a degraded progress bar when voices are unavailable.
- Coverflow snap listener — WHY: translates a settled swipe into `next`/`previous`/`moveTo`, posted to avoid mutating the adapter mid-scroll-layout (`RecyclerView.isComputingLayout` crash).
- Scrubber listener — WHY: on release, seeks to `duration * progress / maxProgress`; drags themselves do not seek, only the drop.
- Settings button → `ListenSettingsFragment.show(...)` — WHY: the gear jumps to voice/engine settings.
- `initSpeedButtonFormat` — WHY: locale-aware speed label ("1.5x") shared with `ListenControlsView`.

## Junior notes
- Three separate `trackedControls` look redundant but each carries its own analytics context (player vs coverflow vs scrubber) — merging them would misattribute events.
- `maxProgress` comes from `R.integer.listen_max_progress` (not 100), so progress math must scale through it; the scrub thumb drawable is set in code for pre-Lollipop vector compat.
