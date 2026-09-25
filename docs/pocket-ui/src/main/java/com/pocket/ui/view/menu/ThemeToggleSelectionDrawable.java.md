# pocket-ui/src/main/java/com/pocket/ui/view/menu/ThemeToggleSelectionDrawable.java

## What this is
The teal ring drawn around the selected swatch in the theme picker: a stroked circle, 2dp thick, themed so it adapts to light/dark mode. It has no content of its own — it is purely the selection indicator.

## How it fits
Created by `ThemeToggle.init()` and painted in `ThemeToggle.drawChild()` directly over the currently selected swatch's bounds. The toggle passes the swatch's drawable state into it, so the ring color resolves against the current theme. It is never used outside the theme toggle.

## Key pieces
- `ThemeToggleSelectionDrawable(context)` — sets up a stroked anti-aliased `Paint` (2dp), a 23.5dp radius, and the themed teal color list (`pkt_themed_teal_2` via `NestedColorStateList`, a helper that resolves theme-dependent colors).
- `draw(canvas)` — resolves the paint color for the current state and draws the circle centered in its bounds.
- `isStateful` / `onStateChange` — declares the drawable state-dependent so theme changes propagate to the color.
- `getIntrinsicWidth/Height` — report the ring diameter (radius × 2, rounded up).
- `setAlpha` / `setColorFilter` / `getOpacity` — stock `Drawable` contract: alpha and filters forward to the paint; opacity is translucent since only a ring is drawn.

## Junior notes
- A `Drawable` here is just "something that can paint itself into a rectangle" — this one ignores most of its bounds except the center, using the fixed radius instead.
- `ColorStateList` = a color that changes with view state (pressed, checked, light/dark theme); `NestedColorStateList.get` unwraps Pocket's layered theme definitions into one.
