# Pocket/src/main/java/com/pocket/app/reader/internal/originalweb/overlay/bottomsheet/OriginalWebBottomSheetFragment.kt
## What this is
The user-visible reader menu floating over the original web page: save/archive, listen, share, switch-to-article, favorite, tags, and delete actions for the page open in the Custom Tab behind it. It renders its ViewModel's state and turns one-shot events into navigation.
## How it fits
Shown by `OriginalWebOverlayActivity` with the page URL as a fragment argument (`stringArg` = a delegated property reading the String from arguments). Hilt injects `Listen` (text-to-speech) for playback. On view creation it calls `viewModel.onInitialized(url)`; `setupEventObserver` (collected with `collectWhenResumed`, i.e. only while resumed) maps each `OriginalWebBottomSheet.Event` to UI: back to `MainActivity`, sign-in, `ShareDialogFragment`, article-view switch (via the static `OriginalWebFragment.resumeAction`), listen playback with expanded player UI, `ItemsTaggingFragment`, or toasts.
## Key pieces
- `onDismiss(...)` — finishes the host overlay activity, but only when audio is `STOPPED` and no listen session just started (`shouldCloseActivityOnDismiss` flips false in `OpenListen` so the sheet can close while the player stays up).
- `OpenListen` handling — starts `listen.trackedControls(null, null).play(track)`, expands the player UI, then dismisses without killing the activity.
- `SwitchToArticleView` handling — sets `OriginalWebFragment.resumeAction` so the underlying fragment swaps to the parsed article when the user returns, then brings `MainActivity` forward.
- `newInstance(url)` — factory packing the URL arg; `ARG_URL` is private to keep callers on the factory.
## Junior notes
- `shouldCloseActivityOnDismiss` is the subtle flag: dismissing the sheet normally tears down the transparent activity, except when handing off to the listen player — get this wrong and audio keeps playing with no UI, or the player dies on dismiss.
- This fragment never touches repositories directly; all save/favorite/delete logic lives in its ViewModel, this file is navigation only.
