# Pocket/src/main/java/com/pocket/sdk/util/dialog/FetchingDialog.java
## What this is
A blocking full-screen loading dialog shown when an activity starts before the user's data has been fetched from the server. It disables all touches on the activity, runs `AppSync.sync()` with a progress bar, and offers retry, close-the-app, or get-help on failure. It dismisses itself when fetching finishes or the activity stops.
## How it fits
`MainActivity.disableInteractionUntilDataIsFetched()` calls `blockInteractionUntilFetched(this, ...)` during startup; when the callback fires it reveals the real content. Under the hood it drives `AppSync.sync()` (the first-run fetch of the user's list) and reports failures through `Help.requestHelp()` with an `ErrorReport`. It hooks the activity lifecycle via `AbsPocketActivity` listeners.
## Key pieces
- `blockInteractionUntilFetched(activity, listener)` — static entry point; returns false (shows nothing) when there is no need: null/finishing activity, already fetched, signed out, or just signed up with nothing to fetch. WHY: most launches skip the blocker entirely.
- `FetchingDialog(activity, listener)` constructor — inflates the loading view, sets an indeterminate rainbow progress circle, marks the window `FLAG_NOT_TOUCHABLE`, and shows a non-cancelable dialog. WHY: there is a brief gap before the dialog appears where touches could otherwise slip through.
- `fetch()` — runs `appSync.sync(onSuccess, onError, onProgress)`; on error shows retry/close/get-help alert, on progress updates the progress circle. WHY: a failed first fetch leaves the app unusable, so the user gets explicit recovery choices.
- `finish()` — idempotent dismiss: clears the touch block, notifies the listener, and unregisters the lifecycle listener on the next loop. WHY: success, stop, and error paths can all race to finish.
- `OnDismissedListener` — callback fired once the blocker is gone. WHY: lets the activity reveal content only after data is ready.
## Junior notes
- The dialog is a plain `Dialog`, not a `DialogFragment`, so it does not survive rotation; the `onActivityStop` hook guarantees it is torn down instead of leaking a window.
- The internal-only long-press shortcut on the help button opens `TCActivity` (team tools); it only exists in company builds, so do not rely on it in release.
