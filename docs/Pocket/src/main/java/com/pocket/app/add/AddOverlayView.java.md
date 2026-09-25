# Pocket/src/main/java/com/pocket/app/add/AddOverlayView.java
## What this is
The quick-save confirmation card shown by `AddActivity` when the save-extension overlay is enabled: a checked save icon with title, a tag button, and a fullscreen loading spinner, all inside a clickable `FrameLayout` (a ViewGroup that stacks children; taps on the backdrop dismiss). It is pure UI: construction inflates the layout and `Binder` wires behavior.
## How it fits
`AddActivity.commitSave` instantiates it, sets the saved/tag click targets (open Pocket / open tagging), and animates it in via `setContentView`; `onSaved` later attaches the real tag handler once the item exists. `showLoading`/`hideLoading` mirror the in-flight save state.
## Key pieces
- Constructor: inflates `ViewAddOverlayBinding` (view binding: type-safe generated refs to the XML layout, no `findViewById`), styles the backdrop, presets the checked icon/title, then `bind().clear()` to a neutral state.
- `Binder`: fluent wiring API (`clear`, `onSavedClick`, `onTagClick`, `showLoading(message)`, `hideLoading`); WHY a fluent binder is consistency with other Pocket custom views and one-line setup at the call site.
- `clear`: nulls both click targets and hides loading; passing null listeners is the supported "disable" path (setters tolerate null).
## Junior notes
- `addTags.setSideMarginStart/End()` are custom `IconButton` helpers, not framework methods; look them up on `IconButton` before assuming standard margin behavior.
- The view sets its own click-to-dismiss on the overlay root; when embedding it elsewhere, remember the root consumes taps, so place buttons above it in z-order.
