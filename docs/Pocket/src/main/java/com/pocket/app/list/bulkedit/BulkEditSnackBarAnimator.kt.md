# Pocket/src/main/java/com/pocket/app/list/bulkedit/BulkEditSnackBarAnimator.kt

## What this is
A tiny slide animation helper for the bulk-edit bottom bar. It moves the bar vertically between hidden (pushed 75dp below the screen) and shown (flush at 0).

## How it fits
Owned by `BulkEditSnackBar`; `show()`/`hide()` are called from the view itself or its `showing` binding adapter. Each animation starts from the last animated value so rapid toggles do not jump.

## Key pieces
- `show()` / `hide()` — WHY: the only public API; they pick the target translationY.
- `setupAnimator(targetValue)` — WHY: runs a 250ms `ObjectAnimator` on `translationY` and tracks `currentValue` in the update listener so interrupted animations resume smoothly.
- `SHOWING = 0f`, `hidden = 75dp` — WHY: "shown" means no offset; "hidden" parks the bar one bar-height off screen using `toPx` for density independence.

## Junior notes
- `ObjectAnimator.ofFloat(view, "translationY", ...)` animates a real view property by name via reflection — the string must match the setter (`setTranslationY`).
- `toPx` converts dp to pixels; never hardcode pixel offsets or the bar will sit wrong on different densities.
