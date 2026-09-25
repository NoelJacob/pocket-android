# Pocket/src/main/java/com/pocket/app/list/bulkedit/BulkEditListItemAnimator.kt
## What this is
This is the slide animation that swaps a list row's action buttons (favorite/share/overflow) for a bulk-edit radio button when the user enters multi-select edit mode. It animates `metaLayout.translationX` (horizontal slide) while cross-fading the two control groups over 250ms.
## How it fits
Owned by `MyListFragment` (which calls `reset()` in `onDestroyView`) and handed to `MyListAdapter`, which calls `showBulkEdit(binding)` / `hideBulkEdit(binding)` per row as edit mode toggles. `maxTranslationX` is measured once from `actionLayout.width` and reused; `currentValue` (0 = actions showing, 1 = radio showing) is the single animation driver shared across rows.
## Key pieces
- `showBulkEdit(binding)` / `hideBulkEdit(binding)` — flip `isShowing`, retarget a `ValueAnimator` from the current value to 1 or 0, and attach a listener applying the interpolated value to that row; no-ops when already at the target avoid restarting the animation per rebind.
- `setCurrentState(binding)` — applies one frame: slides `metaLayout`, fades `actionLayout` vs `bulkEditRadioButton`, and toggles GONE/VISIBLE at the endpoints so invisible controls don't intercept taps.
- `reset()` — clears listeners and state for view teardown; prevents the shared animator from holding dead row bindings.
- `HIDDEN = 0f` / `SHOWING = 1f` / 250ms duration constants.
## Junior notes
- One animator instance drives all rows (`@Inject` no-arg constructor, shared) — `currentValue` is global, so rapidly toggling edit mode mid-animation can leave a row mid-slide; `MyListAdapter` rebinds correct endpoints, so it self-heals on scroll.
- `maxTranslationX == 0f` re-measures per call until layout has run — calling before first layout yields a zero-width slide; the adapter only calls this for laid-out rows, keep it that way.

