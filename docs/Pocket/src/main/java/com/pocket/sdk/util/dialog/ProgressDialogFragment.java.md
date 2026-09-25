# Pocket/src/main/java/com/pocket/sdk/util/dialog/ProgressDialogFragment.java
## What this is
A simple indeterminate spinner dialog fragment (an Android dialog managed by the fragment system, so it survives rotation) used for blocking operations like clearing the cache or changing the data location. It shows a message with no buttons and an optional cancelable flag. Only one instance is ever shown at a time.
## How it fits
Created via `ProgressDialogFragment.getNew(message, tag, cancelable)` and shown with the inherited `ExtendedDialogFragment.show(activity)` from settings or cache-management screens. It builds a framework `ProgressDialog` in `onCreateDialog()`, and its showing flag prevents stacking when the operation is triggered twice.
## Key pieces
- `getNew(message, tag, cancelable)` overloads — factory methods taking a string resource or raw string. WHY: fragments need an empty constructor, so all setup goes through the args bundle.
- `TYPE_CLEARING_CACHE` / `TYPE_CHANGING_DATA_LOCATION` — integer tags identifying which operation the dialog is for. WHY: lets callers and listeners tell concurrent operations apart.
- `onCreateArgs(args)` — stores the cancelable flag into the arguments bundle. WHY: plain fields are lost on rotation, bundle values are not.
- `onCreateDialog(savedInstanceState)` — builds the `ProgressDialog` with the stored message and applies cancel setup. WHY: the fragment manager calls this to (re)create the dialog, including after rotation.
- `isShowingInstance()` / `setShowingInstance(boolean)` — static-flag single-instance guard. WHY: inherits the "never stack two progress dialogs" behavior from `ExtendedDialogFragment`.
## Junior notes
- The `tag` parameter of `getNew()` is accepted but ignored (the message goes through `createArgs(null, message)`); do not rely on it to find the fragment later.
- `setCancellable()` only takes effect if called before `getNew`/`createArgs` flows into `onCreateArgs`; setting it after showing does nothing.
