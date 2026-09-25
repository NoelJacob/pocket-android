# Pocket/src/main/res/layout/view_bulk_edit_snack_bar.xml

## What this is

This layout is the bulk-edit snackbar: action confirmation plus Undo for multi-selections.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `BulkEditSnackBar`, `BulkEditSnackBarAnimator` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewBulkEditSnackBarBinding` class wires views to code).

## Key pieces

- `@id/bulkEditSnackBarText` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/bulkEditReAdd` (`com.pocket.ui.view.themed.ThemedImageView`): interactive element the host fragment/adapter wires up
- `@id/bulkEditArchive` (`com.pocket.ui.view.themed.ThemedImageView`): interactive element the host fragment/adapter wires up
- `@id/bulkEditTrash` (`com.pocket.ui.view.themed.ThemedImageView`): interactive element the host fragment/adapter wires up
- `@id/bulkEditOverflow` (`com.pocket.ui.view.themed.ThemedImageView`): interactive element the host fragment/adapter wires up

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
