# Pocket/src/main/java/com/pocket/util/android/animation/AnimatorEndListener.java

## What this is
An abstract `Animator.AnimatorListener` (the four-callback interface for animation start/end/cancel/repeat) that leaves only `onAnimationEnd` abstract. It solves the clutter of implementing three empty methods every time code cares solely about cleanup after an animation. For example, `ThemeChange` attaches anonymous instances to its background and text-color animators just to remove listeners and restore final colors when the transition ends.

## How it fits
It is used at animation call sites that need end-of-animation teardown. Known users are `ThemeChange` (day/night transition listener cleanup), `TooltipViewsHolder` (counting down multiple tooltip views before finishing), `CaretTooltip` (its `animateOut` callback plumbing via `TooltipView.animateOut`), and `BaseWebView` (stopping the progress-bar rainbow when its fade finishes). It produces nothing downstream beyond the `onAnimationEnd` callback and the cancel flag.

## Key pieces
- `onAnimationEnd(animator)`: the single abstract method subclasses implement. WHY the class exists: this is the only callback most call sites need.
- `onAnimationCancel` / `onAnimationStart` / `onAnimationRepeat`: concrete no-op-ish bookkeeping. WHY overridden: so subclasses are not forced to stub them.
- `wasCanceled()`: reports whether the run was cancelled (set on cancel, cleared on start). WHY it exists: end-handling often differs between "finished naturally" and "was interrupted".

## Junior notes
- Check `wasCanceled()` inside `onAnimationEnd` when the outcome matters: a cancelled animation still triggers `onAnimationEnd` on Android, so "end" does not mean "completed".
- `wasCanceled` is reset on every start, so a reused listener instance is safe across runs — but it is not thread-safe, so attach and use it on the UI thread only.
- If you later need start/cancel/repeat behavior too, override those methods on this class rather than dropping back to the raw four-method interface.
