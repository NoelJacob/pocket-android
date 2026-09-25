# pocket-ui/src/main/java/com/pocket/ui/view/dialog/DialogView.java

## What this is
A simple title-plus-message prompt with up to two text buttons (primary and secondary). The user sees a centered popup card; each button dismisses the popup and then runs its click listener, and any section left unset (title, message, either button) is hidden so the same view covers alerts, confirmations, and plain messages. Height is capped at 309dp so long messages scroll within the card instead of overflowing the screen.

## How it fits
Inflated from `view_dialog_popup` and shown either embedded in a layout or, more commonly, as an `AlertDialog` popup via `bind().showAsAlertDialog(dismissListener, cancelable)`. It extends ThemedRelativeLayout (theme-aware RelativeLayout). Callers (features showing confirmations or errors) chain the fluent Binder (a builder object whose methods return itself for chaining): `bind().title(...).message(...).buttonPrimary(...).showAsAlertDialog(...)`.

## Key pieces
- `bind()`: entry point returning the single reusable Binder; WHY a Binder exists is to keep XML inflation separate from per-show configuration.
- `Binder.clear()`: hides every section so a reused view never leaks the previous dialog's text or buttons; called from `init()`.
- `Binder.title()` / `message()` (int and CharSequence overloads): set or hide each text section via `setTextOrHide` (a helper that hides the view when text is null); the int overload resolves a string resource.
- `Binder.buttonPrimary()` / `buttonSecondary()`: set button text plus the listener stored in `onClickPrimary` / `onClickSecondary`; a null label hides that button.
- `init()` button wiring: each button dismisses `dialog` first, then invokes its listener, so handlers never run against a visible dialog.
- `Binder.showAsAlertDialog()`: detaches the view from any current parent (a view can only have one parent), wraps it in an `AlertDialog`, tracks it in `dialog` for the button auto-dismiss, and clears the reference plus fires the dismiss listener on dismiss.
- `onMeasure()`: clamps measured height to `maxHeight` (309dp) while preserving the measure mode.

## Junior notes
- A View instance can be shown only once at a time: `showAsAlertDialog` removes it from its old parent first, but showing the same DialogView twice needs two instances or re-show after dismiss.
- Button listeners fire AFTER dismiss; do not call `dialog.dismiss()` inside them (already done) and do not assume the dialog is still showing.
- `AlertDialog.Builder.setView()` with an already-parented view throws, which is WHY the parent-removal step exists; the TODO notes the dialog theme may still need an app-compat theme check.
