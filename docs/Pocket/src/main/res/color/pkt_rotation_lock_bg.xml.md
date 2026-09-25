# Pocket/src/main/res/color/pkt_rotation_lock_bg.xml

## What this is

This is a color state list: Background state list for the rotation-lock toggle. Referencing it (instead of a flat `@color/`) makes the view re-tint itself automatically on state changes.

## How it fits

Referenced as `@color/pkt_rotation_lock_bg` from layouts/drawables; used by PktRotationLockView.java. State lists live here (not in the shared color module) because they are app-specific state mappings.
