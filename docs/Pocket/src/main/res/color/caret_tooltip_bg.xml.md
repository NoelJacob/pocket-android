# Pocket/src/main/res/color/caret_tooltip_bg.xml

## What this is

This is a color state list: Background color state list for tooltip carets. Referencing it (instead of a flat `@color/`) makes the view re-tint itself automatically on state changes.

## How it fits

Referenced as `@color/caret_tooltip_bg` from layouts/drawables; used by CaretTooltip.java. State lists live here (not in the shared color module) because they are app-specific state mappings.
