# Pocket/src/main/java/com/pocket/util/android/ViewUtil.java

## What this is
A grab-bag of static view helpers that remove the null checks, loops, and API quirks from everyday Android view code. It solves a dozen small pains in one place: showing/hiding groups of views, tweaking padding or layout size without disturbing other edges, forcing keyboard focus, fading views, and hit-testing touch coordinates. For example, `ListenControlsView` toggles whole clusters of player buttons with one `ViewUtil.setVisibility(...)` call, and `ListenSpeedControlsPopup` makes itself dismiss on outside touch with `setCancelOnOutsideTouch`.

## How it fits
It is called from view and fragment code across the app: `ListenControlsView`, `ListenPlayerView`, `ListenView` (visibility, playlist hit-testing), `CoverflowView` (horizontal padding), `ListenSpeedControlsPopup`, `ItemsTaggingFragment`, `WebViewTextFinder`, and `PocketUiPlaygroundActivity` (`refreshDrawableStateDeep` on theme switch). It delegates nothing app-specific — pure Android view operations — and returns nothing downstream beyond the mutated views.

## Key pieces
- `isVisible(view)` / `setVisible(...)` / `setVisibility(visibility, views...)`: null-tolerant bulk visibility. WHY they exist: toggling N buttons without N null checks is the single most repeated view chore.
- `setPaddingBottom/Left/Right/Horizontal(view, padding)`: single-edge padding setters. WHY they exist: `setPadding` forces all four edges, so changing one edge inline risks clobbering the others.
- `setLayoutWidth(view, width)` / `setLayoutHeight(view, height)`: resize via layout params, creating params if missing. WHY they exist: safe resizing without assuming params already exist.
- `forceFocus(focus, view)` / `forceSoftKeyboard(open, view)`: focus plus explicit keyboard show/hide. WHY they exist: focusing a view does not reliably summon the keyboard on all devices.
- `fadeView(view, visible, duration)`: alpha fade in/out via `AlphaAnimation`. WHY it exists: one call for the common show/hide-with-fade.
- `getChildViewForCoord(parent, x, y)` / `containsView(parent, view)`: hit-testing and ancestry checks. WHY they exist: touch routing (e.g. `ListenView` deciding whether a touch lands in the sticky player).
- `setCancelOnOutsideTouch(popup)`: transparent background + outside-touchable + focusable for `PopupWindow`. WHY bundled: all three flags are required together or outside-tap dismissal silently fails.
- `refreshDrawableStateDeep(view)`: recursive drawable-state refresh. WHY it exists: theme switches must propagate new states down the whole tree.
- `setProgress(bar, percent)`: 0..1 float onto a `ProgressBar`'s int scale. WHY it exists: callers think in fractions, bars think in max units.
- `moveTouchEventIntoBounds(event, view)` / `getParent(child, steps)`: touch-coordinate clamping and ancestor walking. WHY they exist: shared fiddly math for custom touch handling.

## Junior notes
- The varargs (`View... views`) bulk setters skip nulls silently — convenient, but a null in the list means a bug elsewhere is being hidden; fix the source when you spot one.
- `setPadding*` helpers read the current padding for untouched edges, so they are safe to call after XML padding is applied — but they overwrite values set by background drawables that manage their own padding.
- `fadeView` uses the legacy view-animation (`AlphaAnimation`), not property animators: it fades pixels without changing real `alpha`/clickability, so pair it with a visibility change at the end for interactive views.
