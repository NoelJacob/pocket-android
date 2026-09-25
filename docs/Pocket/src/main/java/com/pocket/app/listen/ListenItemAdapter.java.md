# Pocket/src/main/java/com/pocket/app/listen/ListenItemAdapter.java

## What this is
Adapter for the Listen bottom-sheet playlist: position 0 is the full player header (`ListenPlayerView`), and every row after it is one queued article rendered as a standard item row with a playing-track indicator.

## How it fits
Owned by `ListenView`, which calls `bind(state, controls, shouldShowDegradedView)` on every text-to-speech state update. Tapping a row tells `ListenView`'s click handler to `moveTo(position)` + `play()` through playlist `Controls`. The header player view is passed in so the adapter can forward bottom-sheet slide offsets to it for the sticky effect.

## Key pieces
- `HEADER_COUNT = 1` + `getItemViewType` — WHY: position 0 inflates the player layout, the rest inflate item rows; offsets (`position - HEADER_COUNT`) map rows to playlist indices.
- `bind(state, controls, degraded)` — WHY: full refresh only when the track list itself changed; otherwise just rebinds the header and the old/new playing rows — this keeps audio-position ticks from redrawing the whole playlist.
- `ItemRowHolder` — WHY: binds title/thumbnail and paints the teal audio-bars `selectionIndicator` on the currently playing row.
- `ListenControlsHolder` — WHY: wraps the shared `playerView` instance as the header row.
- `applyBottomSheetOffset` — WHY: forwards drag position so the player can collapse into its sticky mini form as the sheet slides.

## Junior notes
- `getItemCount` assumes `state` is non-null after the first bind — `ListenView` must call `bind` before the RecyclerView lays out, or this crashes.
- Row clicks compare against `state.index` and ignore taps on the already-playing track; the actual moveTo/play logic lives in `ListenView`, not here.
