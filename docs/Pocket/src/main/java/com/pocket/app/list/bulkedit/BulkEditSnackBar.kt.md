# Pocket/src/main/java/com/pocket/app/list/bulkedit/BulkEditSnackBar.kt

## What this is
The bottom action bar that appears while the user multi-selects saves in bulk-edit mode. It shows a count label plus archive/re-add, delete, and overflow buttons, and slides in and out with an animator.

## How it fits
Placed in the My List screen layout and driven by databinding (XML layout attributes bound to ViewModel fields) from `MyListViewModel`'s bulk-edit state. The screen wires each button via the `setOn*ClickedListener` setters to ViewModel actions; the inner `BulkEditSnackBarAnimator` performs the slide. The companion `@BindingAdapter`s are what the XML actually calls.

## Key pieces
- `show()` / `hide()` — WHY: delegate to the animator so callers never touch translation values directly.
- `setOnReAddClickedListener`, `setOnArchiveClickedListener`, `setOnDeleteClickedListener`, `setOnOverflowClickedListener`, `setOnTextClickListener` — WHY: thin click-forwarding seams; the ViewModel owns what each action does.
- `setShowing` binding adapter — WHY: maps a boolean `showing` XML attribute to show/hide animation.
- `setText`, `setActionsEnabled`, `setArchiveMode`, `setTextClickable` binding adapters — WHY: let XML drive the count text, enable/disable buttons with no selection, and swap archive vs re-add icons depending on Saves vs Archive tab.

## Junior notes
- Root layout in XML is a `<merge>` tag, so orientation/background/gravity must be set in code in the `also` block — that is normal here, not a hack.
- Databinding adapters must be `@JvmStatic` inside a companion object so the generated binding class can call them as static methods.
