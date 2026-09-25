# Pocket/src/main/java/com/pocket/app/list/list/loading/SkeletonListFadeAnimator.kt

## What this is
A polish helper that fades in the grey placeholder ("skeleton") rows while the Saves list loads, so fast loads look smooth instead of flashing.

## How it fits
Created by the My List fragment around its `SkeletonList` loading view. It collects `MyListViewModel.uiState` while resumed and fades the skeleton in whenever the screen enters `Loading` or `SearchLoading`; any other state just arms it for the next load.

## Key pieces
- `init` collector — WHY: watches `screenState` and triggers the fade exactly on transitions into loading states.
- `fadeIn()` — WHY: runs a 1-second alpha 0→1 `ObjectAnimator` once per load; the `isShowing` guard stops re-fading on every re-emission.
- Resetting `isShowing = false` on non-loading states — WHY: re-arms the one-shot so the *next* load fades again.

## Junior notes
- `repeatOnResumed` means collection pauses when the fragment is backgrounded — the animator will not leak or animate off-screen.
- Only the fade-*in* is animated; hiding is instant (handled by swapping the skeleton out), which is why there is no `fadeOut`.
