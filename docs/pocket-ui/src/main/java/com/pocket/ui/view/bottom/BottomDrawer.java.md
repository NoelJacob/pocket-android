# pocket-ui/src/main/java/com/pocket/ui/view/bottom/BottomDrawer.java

## What this is
A Pocket-styled sliding panel (bottom sheet) that rises from the bottom of the screen over the current content. The user sees a rounded-top sheet with an optional dimmed backdrop (scrim = the dark overlay behind the sheet); it can rest collapsed, expand to full height, or hide away. Screens drive it with `expand()`, `collapse()`, `hide()`, and `showAsDialog()`.

## How it fits
A hosting screen places a `BottomDrawer` in its layout (optionally declaring `app:sheetLayout` for the sheet content) and calls `expand()`/`collapse()` to show it; content layouts inflate into the sheet container. Internally it inflates `R.layout.view_bottom_sheet` (scrim, nav/back/title chrome, and the sheet container), attaches a `PktBottomSheetBehavior` to the container, paints the container with `BottomSheetBackgroundDrawable`, and forwards state/slide events to registered callbacks. Subclasses override `onLazyInflated()` for setup after inflation.

## Key pieces
- `inflate(startState)`: one-time lazy setup — inflates the chrome layout, grabs scrim/nav/back/title/content views, wires the sheet behavior with the internal `BottomSheetCallback`, installs the rounded background, inflates the caller content, then starts hidden and animates to `startState` after the next layout. WHY lazy: the sheet costs nothing until first shown.
- `expand()/collapse()/hide()`: public state drivers; `expand`/`collapse` auto-inflate on first call, while `hide()` is a no-op until inflated (nothing to hide yet).
- `setLayout(int)/setLayout(View)`: supplies the sheet content, either as a layout resource (stored and inflated during or after `inflate`) or as a ready view (must be called after inflation).
- `setScrimAlpha(hidden, collapsed, expanded)` + `applyBottomSheetOffset(slideOffset)`: maps the sheet's slide position to backdrop darkness by interpolating between the three configured alphas (`RangeF` = float-range interpolation helper), toggling the scrim between VISIBLE and GONE.
- `BottomSheetCallback` (inner): keeps scrim alpha, accessibility hiding (`AccessibilityUtils.BottomSheetHelper` hides the content behind the sheet from TalkBack when open), and fan-out to external callbacks in sync with drag/settle events; guards against a NaN slide offset from the support library near expanded state.
- `showAsDialog()`: detaches the drawer into a `Dialog` that dismisses itself when the sheet hides, routing the back key to `hide()`.
- `getBehavior()/addBottomSheetCallback()/removeBottomSheetCallback()`: access to the underlying sheet behavior and its event listeners; `matchParentHeight()` switches the sheet to full height for scrolling content like RecyclerViews.

## Junior notes
- `BottomDrawer` extends `CoordinatorLayout`, which is required because `BottomSheetBehavior` only works as a `CoordinatorLayout` behavior attached to a direct child.
- `PktBottomSheetBehavior` is the Pocket subclass of the Material `BottomSheetBehavior` state machine (HIDDEN, COLLAPSED, EXPANDED, DRAGGING, SETTLING); states arrive via `onStateChanged`, continuous drag progress via `onSlide`.
- `hideOnOutsideTouch` plus the scrim touch listener is what makes tapping the dimmed area dismiss the sheet; it only acts on ACTION_DOWN when the flag is on.
