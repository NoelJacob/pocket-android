# Pocket/src/main/java/com/pocket/app/listen/ListenView.kt

## What this is
The main Listen experience: a bottom-sheet drawer with the full player (art carousel, scrubber, controls, playlist) that collapses into a mini player bar so users can keep reading while audio plays.

## How it fits
Hosted by activities that enable Listen UI; it grabs the shared `Listen` text-to-speech service from `App`, creates tracked `Controls` for the sheet, playlist, mini player, and errors (each with its own analytics context), and renders every `ListenState` pushed from the service. Playlist taps, mini-player buttons, error retries, and coverflow swipes all funnel back through those `Controls`. It also emits collapsed/expanded `State` for hosts that need to adjust layout.

## Key pieces
- `bind(state)` — WHY: the master render: mini-player icon/progress/title, sticky header headline, sticky `ListenControlsView`, playlist adapter, and error snackbar; picks degraded vs full player when voices are missing.
- `expand()` / `collapse()` / `isExpanded` / `states` — WHY: bottom-sheet control plus an RxJava stream of collapsed/expanded transitions for hosts.
- Playlist adapter click handler — WHY: tapping a non-playing row issues `moveTo` + `play` on the playlist controls.
- Sticky-player scroll listener — WHY: shows the compact sticky header once the big player scrolls out of view inside the sheet.
- `unbind()` — WHY: clears state when the view is parked so stale track data never flashes on next open.
- Error snackbar + offline tooltip — WHY: surfaces playback failures and offline restriction without leaving the current screen.

## Junior notes
- `BottomSheetBehavior` states drive everything: only `COLLAPSED` and `EXPANDED` are surfaced; intermediate drag states are for the offset animations, not logic.
- `expand()` before first layout must defer via `runAfterNextLayoutOf` — calling `setState(EXPANDED)` too early is silently ignored by the behavior.
