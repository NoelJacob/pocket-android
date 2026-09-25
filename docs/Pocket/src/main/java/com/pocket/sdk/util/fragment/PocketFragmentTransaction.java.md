# Pocket/src/main/java/com/pocket/sdk/util/fragment/PocketFragmentTransaction.java
## What this is
A `FragmentTransaction` (Android's builder for batched fragment add/remove/replace operations) that records what it did and reports back to `PocketFragmentManager` on commit. Every mutating call (`add`, `replace`, `show`, `hide`, `remove`, `attach`, `detach`) is forwarded to the real transaction while noting affected fragments; every `commit*` variant first notifies the manager. Non-mutating settings pass straight through.
## How it fits
Obtained from `PocketFragmentManager.beginTransaction()`; screens use it exactly like a normal transaction (`beginTransaction().replace(...).addToBackStack(...).commit()`). At commit the manager's `onCommit()` updates its visible-fragment ledger and fires focus callbacks, keeping `getVisibleFragments()` correct.
## Key pieces
- `mAdded` / `mRemoved` — fragments touched by this transaction. WHY: the commit-time report is what keeps the manager's ledger exact.
- `add()` / `replace()` / `show()` / `attach()` record into added; `hide()` / `remove()` / `detach()` record into removed; `replace()` also looks up the fragment being replaced. WHY: visibility is derived from adds minus removes.
- `addToBackStack(name)` — sets `mIsAddedToBackStack` so the manager pushes a new ledger entry instead of mutating the current one. WHY: back navigation must restore the previous entry's visible set.
- `commit()` / `commitAllowingStateLoss()` / `commitNow()` / `commitNowAllowingStateLoss()` — each calls `onCommit()` before delegating. WHY: whichever commit style the caller picks, the ledger update cannot be skipped.
## Junior notes
- Mutating calls return `this` (the wrapper), but pure pass-throughs like `setTransition()` return the inner transaction; keep chaining mutating calls on the wrapper so they get recorded.
- This class only tracks fragments for the ledger; animations, breadcrumbs, and transitions behave exactly like a normal transaction.
