# Pocket/src/main/res/layout/view_font_choice_cell.xml

## What this is

This layout is one font option row in the reader font picker (name rendered in that typeface).

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `FontSettingsAdapter` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewFontChoiceCellBinding` class wires views to code).

Included or previewed by: `fragment_font_settings_bottom_sheet.xml`.

## Key pieces

- `@id/premiumIcon` (`com.pocket.ui.view.themed.ThemedImageView`): interactive element the host fragment/adapter wires up
- `@id/fontName` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/upgrade` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/selectedCheck` (`com.pocket.ui.view.themed.ThemedImageView`): interactive element the host fragment/adapter wires up

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
