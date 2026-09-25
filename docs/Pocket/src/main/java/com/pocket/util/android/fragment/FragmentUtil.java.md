# Pocket/src/main/java/com/pocket/util/android/fragment/FragmentUtil.java
## What this is
Static helpers for the two things every screen does with fragments (reusable UI screens hosted inside an activity): safely checking whether a fragment's activity is gone, and adding, showing, or removing fragments. It also defines the `FragmentLaunchMode` enum (dialog vs. new activity vs. activity-that-looks-like-a-dialog) used across the app.
## How it fits
Called from activities and navigation code such as `AuthenticationActivity`, `PremiumMessageActivity`, `ItemsTaggingActivity`/`ItemsTaggingFragment`, `ExtendedDialogFragment`, `PocketFragmentManager`, and `AbsPocketFragment`. `AbsPocketActivity.setContentFragment()` consumes `FragmentLaunchMode`, and `getRootView()` bridges `AbsPocketFragment.getViewRoot()` vs plain `Fragment.getView()`. It produces committed `FragmentTransaction`s (batched fragment operations).
## Key pieces
- `FragmentLaunchMode` (DIALOG / ACTIVITY / ACTIVITY_DIALOG) — WHY: one vocabulary for "show inline, push a screen, or push a screen that renders as a dialog on tablets". Usage in words: pass it to `setContentFragment()` and let form-factor logic decide.
- `isDetachedOrFinishing(frag)` — WHY: the safe "can I touch the UI?" check; true when the fragment is null-safe-checked, unattached, or its activity is finishing. Usage in words: bail out of async callbacks when this returns true.
- `isFinishing(frag)` — WHY: narrower check for activity-finishing only; returns false for detached fragments, so never use it as a null/attach check.
- `addFragment(...)` — WHY: adds a content fragment with optional back-stack and immediate execution. Usage in words: show a screen inline in a container view id.
- `addFragmentAsDialog(...)` — WHY: shows a `DialogFragment` via `frag.show()`, the only correct way to present dialogs. Usage in words: pop a dialog from an activity with an optional tag.
- `removeFragment(frag, activity)` — WHY: symmetric teardown via a remove transaction.
- `getRootView(fragment)` — WHY: hides the `AbsPocketFragment` vs platform fragment difference when callers need the root view.
## Junior notes
- `addFragment` defaults to executing pending transactions immediately; that makes back-to-back calls safe but MUST NOT be called after `onSaveInstanceState`, or it throws `IllegalStateException`.
- `isDetachedOrFinishing(null)` returns false (not true); always null-check separately before using the fragment.
- `ACTIVITY_DIALOG` screens should call `cancelOutsideTouch` in `onActivityCreated` per the enum docs, or outside taps dismiss them unexpectedly.

