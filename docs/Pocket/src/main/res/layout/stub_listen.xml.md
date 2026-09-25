# Pocket/src/main/res/layout/stub_listen.xml

## What this is

This layout is the lazy-inflated stub that hosts the listen player only when text-to-speech starts.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Included or previewed by: `ril_root.xml`.

## Key pieces

- Static hierarchy with no databinding variables or ids: purely structural/styling.

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
