# Pocket/src/main/res/layout/view_storage_location_option.xml

## What this is

This layout is one storage-location radio row (internal vs removable) in Settings.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `StorageLocationPickerDialog` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewStorageLocationOptionBinding` class wires views to code).

## Key pieces

- `@id/radio` (`com.pocket.ui.view.menu.RadioButton`): interactive element the host fragment/adapter wires up
- `@id/label` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/sub_label` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/bullets` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
