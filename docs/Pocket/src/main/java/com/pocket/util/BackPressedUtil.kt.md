# Pocket/src/main/java/com/pocket/util/BackPressedUtil.kt

## What this is
A back-button dispatcher that lets nested fragments intercept and consume back presses, much like views intercept touch events. It solves the problem that Android delivers back presses to the Activity, while the screen that knows what "back" means is often a deeply nested fragment. For example, `MainActivity.onBackPressed()` asks this helper first, and only pops the navigation stack itself if no fragment consumed the event.

## How it fits
It is called from `MainActivity.onBackPressed()` with the Activity's `supportFragmentManager`. It walks the tree of `AbsPocketFragment` children (the base class for Pocket fragments, which declares `onInterceptBackPressed()` and `onBackPressed()`), and returns true when some fragment consumed the press. `AbsPocketFragment` documents itself as the counterpart to this helper.

## Key pieces
- `onBackPressed(fragmentManager)`: the entry point. Runs a two-phase pass over the fragment list and reports whether the event was consumed. WHY it exists: centralizes back-press routing so Activities stay dumb.
- `createBreadthFirstFragmentList(fragmentManager)`: flattens the nested fragment tree into a breadth-first list (top-level fragments first, deepest children last). WHY: defines the traversal order the two phases rely on.
- `addChildren(fragmentList, parentFragments)`: recursive step that appends only visible `AbsPocketFragment` children, level by level. WHY: invisible fragments must not steal back presses from the screen the user actually sees.

## Junior notes
- Two phases: first it walks down looking for a fragment that intercepts (claims priority), then it walks back up from there asking each fragment to consume. If nobody intercepts, the upward pass starts at the deepest fragment.
- Only `isVisible` child fragments are considered; detached or hidden fragments are skipped.
- `NavHostFragment` import is a leftover signal of the navigation setup; the traversal itself works off plain `childFragmentManager` trees.
