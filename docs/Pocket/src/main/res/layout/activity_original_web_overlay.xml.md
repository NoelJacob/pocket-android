# Pocket/src/main/res/layout/activity_original_web_overlay.xml

## What this is

This layout is the overlay activity shown on top of the original-web view (share/error chrome).

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `OriginalWebOverlayActivity` (databinding = XML layouts bound to ViewModel fields, so the generated `ActivityOriginalWebOverlayBinding` class wires views to code).

## Key pieces

- Static hierarchy with no databinding variables or ids: purely structural/styling.

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
