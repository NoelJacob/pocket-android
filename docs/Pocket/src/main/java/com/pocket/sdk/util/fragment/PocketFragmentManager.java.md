# Pocket/src/main/java/com/pocket/sdk/util/fragment/PocketFragmentManager.java
## What this is
A wrapper around Android's `FragmentManager` (the system service that adds, removes, and back-stacks UI pieces called fragments) that tracks which fragments are visible at each back-stack entry. It adds focus callbacks (`onLostFocus` / `onRegainedFocus`), back-press and restart/theme fan-out to fragments, and save/restore of its own visible-fragment bookkeeping. It is the fragment manager every Pocket activity actually uses.
## How it fits
Each `AbsPocketActivity` owns one instance wrapping `getSupportFragmentManager()` and exposes it as `getPocketFragmentManager()`; `beginTransaction()` returns a `PocketFragmentTransaction` that reports commits back here. `AbsPocketFragment` screens receive focus, back-press, restart, and theme events through it, and activities like `MainActivity` and tagging screens query `getVisibleFragments()` / `findFragmentByTag()`.
## Key pieces
- `beginTransaction()` — returns a `PocketFragmentTransaction` tied to this manager. WHY: every commit flows through `onCommit()` so the visible-fragment ledger stays exact.
- `onCommit(added, removed, addedToBackStack)` — updates the current entry's added/visible lists, or pushes a new back-stack entry with focus-loss callbacks. WHY: Android itself does not remember "which fragments were visible per entry", which Pocket needs.
- `onBackStackChanged()` — pops ledger entries to match, fires regained-focus callbacks, and reports an unexpectedly empty stack. WHY: popping the system stack must restore Pocket's focus bookkeeping too.
- `getVisibleFragments()` — the currently visible fragments across entries. WHY: analytics (`getActionViewName`), parent lookup, and back handling all need this, not the raw fragment list.
- `onBackPressed()` / `onActivityRestart()` / `onThemeChanged(newTheme)` — fan out to `AbsPocketFragment`s newest-first, stopping when one handles the back press. WHY: fragments get first refusal on back before the activity finishes.
- `onSaveInstanceState()` / `onRestoreInstanceState()` — persists per-entry added/visible sets as index arrays into the manager's fragment list. WHY: rotation and process death must rebuild the same visible ledger.
- `finishFragment(fragment, activity)` — dismisses dialogs, finishes the activity for root fragments, else removes the fragment. WHY: one "close this screen" call that does the right thing per fragment kind.
- `removeAllFragments()` — removes every visible fragment (used on logout). WHY: guarantees no signed-in UI lingers after the user signs out.
## Junior notes
- All delegation methods pass through to the wrapped manager except `beginTransaction()`; never hold the raw `getSupportFragmentManager()` and commit around this class or the ledger drifts from reality.
- `getVisibleFragments()` returns the live internal list; read it but never add or remove items.
- An empty back stack after a pop is reported as an error (thrown in dev, reported in production); if you see it, an activity is likely finishing mid-pop rather than real corruption.
