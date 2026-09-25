# Pocket/src/main/java/com/pocket/sdk/util/dialog/ExtendedDialogFragment.java
## What this is
A convenience layer over `RilDialogFragment` that standardizes title/message arguments and showing. Subclasses get `createArgs()` helpers for bundling their title and message, plus `show(activity)` that safely falls back to whatever activity is currently on screen. It also supports "only one instance at a time" dialogs.
## How it fits
The direct parent of concrete dialogs such as `ProgressDialogFragment`. Screens call `MyDialog.getNew(...).show(activity)`; `show()` posts the fragment via `FragmentUtil.addFragmentAsDialog`, or via `App.getActivityContext()` when no activity is passed. `onCreate()` restores the persist flag inherited from `RilDialogFragment`.
## Key pieces
- `show(activity)` / `showOnCurrentActivity()` — shows on the given activity, or the current foreground one posted to the UI thread. WHY: background callbacks often have no activity reference but still need to show a dialog.
- `onlyAllowOneInstanceAtATime()` + `isShowingInstance()` / `setShowingInstance(boolean)` — subclasses keep a static boolean flag so repeated `show()` calls while one is visible are ignored. WHY: database or file errors firing from many places would otherwise flood the screen with stacked dialogs.
- `createArgs(...)` overloads — pack title/message (as resource ids or strings) into the fragment arguments bundle. WHY: fragment arguments survive rotation, plain fields do not.
- `onCreateArgs(args)` — hook for subclasses to add their own values to the bundle. WHY: custom data (e.g. a progress type) survives rotation the same way.
- `onCreate()` — marks the showing flag and, if restored with `mShouldPersist == false`, hides and dismisses itself. WHY: transient dialogs do not reappear after rotation.
- `onClose(isCancel)` — clears the showing flag so the dialog can be shown again later. WHY: without this the single-instance guard would lock the dialog off forever.
## Junior notes
- Fragments must keep an empty constructor; always pass data through `createArgs()` before showing, never through a custom constructor, or rotation will lose it.
- The static showing-flag pattern means one flag per dialog class; copying a dialog without copying its flag methods silently breaks the single-instance guard.
