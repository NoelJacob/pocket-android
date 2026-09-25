# Pocket/src/main/java/com/pocket/sdk/util/dialog/AlertMessaging.java
## What this is
A static helper for showing Pocket-styled alert dialogs (simple pop-up message boxes with buttons) anywhere in the app. It wraps `AlertDialog.Builder` with null-safe and finishing-safe guards, plus standard error dialogs that know about offline state and support emails. Every method is safe to call with a dead or finishing screen: it just returns null and shows nothing.
## How it fits
Called directly from activities, fragments, and error paths across the app (e.g. `FetchingDialog`'s fetch failure, connection-dependent operations). It builds plain `AlertDialog` / `ProgressDialog` objects on the given screen and routes "Get Help" buttons into `Help.requestHelp()`. `dismissSafely()` is used by screens dismissing dialogs from async callbacks that may arrive after the screen is gone.
## Key pieces
- `show(...)` overloads — build and show an OK / OK-Cancel style dialog from either string resources or raw text, returning the shown dialog or null. WHY: one safe choke point instead of every caller checking activity state.
- `isContextUnavailable(context)` — true when the context is null or its Activity is finishing. WHY: showing a dialog then throws, so every `show` checks this first.
- `showConnectionDependantError(...)` — shows a "no connection" dialog when offline, otherwise the friendly message for the error. WHY: connection failures get a specific message instead of a generic crash-style error.
- `showError(...)` — error dialog with an OK button plus an optional Get Help button, and a hidden long-press on OK that offers to report the error. WHY: support requests carry the `ErrorReport` without cluttering the normal dialog.
- `askIfTheyWantToReport(...)` — Yes/No prompt that opens a support email with the error attached. WHY: explicit user consent before sending a report.
- `progress(...)` — shows an indeterminate spinner dialog. WHY: quick blocking feedback for short operations.
- `dismissSafely(dialog, context)` — dismisses if possible, swallowing the "window detached" crash when the screen is already gone. WHY: async callbacks cannot reliably track screen lifetime.
## Junior notes
- Dialogs here are framework `AlertDialog`s shown directly, not `DialogFragment`s (dialogs managed by the fragment system that survive rotation); they do not survive screen rotation, so for rotation-safe dialogs use `ExtendedDialogFragment` subclasses instead.
- A resource id of 0 means "no text" in the int overloads; the `opt()` helper converts it to null so callers can pass 0 for optional titles and messages.
