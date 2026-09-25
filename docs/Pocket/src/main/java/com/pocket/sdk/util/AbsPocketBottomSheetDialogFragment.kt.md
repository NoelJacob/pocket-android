# Pocket/src/main/java/com/pocket/sdk/util/AbsPocketBottomSheetDialogFragment.kt
## What this is
A base class for bottom-sheet dialogs (panels that slide up from the bottom of the screen). It applies the app's transparent bottom-sheet theme and fixes a layout bug where sheets misbehave in landscape orientation.
## How it fits
Extended by dialog screens shown from an AbsPocketActivity via the fragment manager. It sits on top of Material's BottomSheetDialogFragment and only adjusts the BottomSheetBehavior (expanded state, max width) after the view is created.
## Key pieces
- `onViewCreated`: forces the sheet to STATE_EXPANDED with skipCollapsed in landscape, and caps width to home_max_width so tablets don't stretch full-width.
- `getTheme`: returns TransparentBottomSheetDialogTheme so the sheet has rounded/translucent styling.
## Junior notes
- BottomSheetDialogFragment is a Material component: a Fragment displayed as a sliding panel. Behavior is controlled via BottomSheetBehavior.from(view).
- The landscape workaround targets an old Material v1.0.0 bug; safe to remove after the library is upgraded.
