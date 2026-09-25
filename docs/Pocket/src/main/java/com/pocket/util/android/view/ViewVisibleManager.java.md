# Pocket/src/main/java/com/pocket/util/android/view/ViewVisibleManager.java
## What this is
The visibility twin of `ViewEnabledManager`: an AND-gate for whether a view shows. Register `ViewVisibilityCondition`s; the view is `VISIBLE` only while all return true, otherwise it drops to a caller-chosen hidden state (`GONE`, which frees layout space, or `INVISIBLE`, which keeps the space).
## How it fits
Used by `ItemsTaggingFragment` for its sticky header (in words: `new ViewVisibleManager(headerFixed, View.GONE)` plus scroll/selection conditions) and by `ChipEditText` for the clear-text button. It consumes condition callbacks and produces `view.setVisibility(...)` calls, returning whether the state actually flipped.
## Key pieces
- `ViewVisibleManager(view, hiddenVisibility)` — WHY: binds one view plus its off-state; pass `GONE` to collapse space or `INVISIBLE` to hold it. Usage in words: create after inflation with the desired hidden behavior.
- `addCondition(enabler)` — WHY: adds one AND vote and immediately refreshes.
- `invalidate()` — WHY: recomputes and applies visibility, returning true if it changed (useful for follow-up layout work). Usage in words: call whenever any condition's answer may have flipped.
- `ViewVisibilityCondition.isVisible()` — WHY: the per-rule vote to implement.
## Junior notes
- Like its sibling, an empty condition list means visible, and conditions are polled only on `addCondition`/`invalidate`; no auto-observation.
- `GONE` vs `INVISIBLE` is fixed at construction; if different rules need different hidden states, you need two managers or manual code.
- The boolean return of `invalidate()` is for "did it change", not current visibility; compare against `view.getVisibility()` if you need the latter.

