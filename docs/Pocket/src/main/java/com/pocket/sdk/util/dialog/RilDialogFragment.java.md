# Pocket/src/main/java/com/pocket/sdk/util/dialog/RilDialogFragment.java
## What this is
The base class for all Pocket dialogs managed as fragments (a `DialogFragment` is an Android dialog whose lifetime is managed by the fragment system, so it survives screen rotation). It adds safe cancel setup, an `OnCloseListener` that distinguishes dismiss from cancel, and a state-loss-safe dismiss workaround. Every Pocket dialog fragment ultimately extends this.
## How it fits
Extended by `ExtendedDialogFragment`, which adds title/message args and single-instance guards; concrete dialogs like `ProgressDialogFragment` sit on top of that. Screens show these through the fragment manager (`FragmentUtil.addFragmentAsDialog`), and listen for close events via `setOnCloseListener`. `AbsPocketActivity` and back-press handling interact with these fragments.
## Key pieces
- `dialogCancelableSetup(fragment, dialog, cancelable)` — applies cancelable mode, including swallowing the search key on non-cancelable dialogs and allowing outside-touch cancel otherwise. WHY: old devices could dismiss "blocking" dialogs with the hardware search button.
- `onDismiss()` / `onCancel()` / `onDestroy()` all funnel into `onClose(isCancel)` — guarantees the close listener fires exactly once no matter how the dialog went away. WHY: callers get one reliable callback instead of three competing ones.
- `setOnCloseListener(listener)` / `OnCloseListener` — `onCancel` vs `onDismiss` callbacks. WHY: lets callers tell "user backed out" apart from "dialog finished". Note it is not restored across rotation.
- `mShouldPersist` / `STATE_SHOULD_PERSIST` — saved across rotation; when false the dialog hides itself after restore. WHY: some dialogs (e.g. transient progress) must not pop back up after rotation.
- `dismissAllowingStateLoss()` — dismisses even if the fragment manager already saved state, working around the old support library missing this API. WHY: dismissing after `onSaveInstanceState` otherwise throws.
- `getStringSafely(res)` — loads a string from the app context instead of the fragment. WHY: after detach the fragment has no resources and plain `getString()` crashes.
## Junior notes
- `DialogFragment` lifecycle methods (`onDismiss`, `onCancel`, `onDestroy`) can each fire for one close; never put logic in just one of them, use the `OnCloseListener` or `onClose()`.
- `setOnCloseListener` does not survive process death or rotation; re-set it every time you create or restore the dialog host.
