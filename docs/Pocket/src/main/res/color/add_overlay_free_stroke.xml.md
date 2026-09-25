# Pocket/src/main/res/color/add_overlay_free_stroke.xml

## What this is

This is a color state list: Stroke color state list for the save-confirmation overlay. Referencing it (instead of a flat `@color/`) makes the view re-tint itself automatically on state changes.

## How it fits

Referenced as `@color/add_overlay_free_stroke` from layouts/drawables; used by AddOverlayView.java, DialogSizeWrapper.java. State lists live here (not in the shared color module) because they are app-specific state mappings.
