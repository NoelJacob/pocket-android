# Pocket/src/main/res/layout/rotation_lock.xml

## What this is

This layout is the rotation-lock prompt container.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Included or previewed by: `ril_root.xml`.

## Key pieces

- Static hierarchy with no databinding variables or ids: purely structural/styling.

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
