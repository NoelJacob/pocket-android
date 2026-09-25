# Pocket/src/main/res/layout/view_prefs_header_layout.xml

## What this is

This layout is the section header row inside Settings preference lists.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `PremiumSettingsFragment` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewPrefsHeaderLayoutBinding` class wires views to code).

## Key pieces

- Static hierarchy with no databinding variables or ids: purely structural/styling.

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
