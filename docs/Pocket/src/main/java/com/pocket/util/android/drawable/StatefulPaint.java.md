# Pocket/src/main/java/com/pocket/util/android/drawable/StatefulPaint.java
## What this is
An Android `Paint` (the style object a `Canvas` uses when drawing: color, stroke, text settings) whose color follows a `ColorStateList` (a resource mapping states like pressed/disabled to colors). Call `setState()` with the view's drawable state and the paint recolors itself.
## How it fits
Driven by `SimpleStatefulDrawable.onStateChange()` and directly by `ResizeDetectLinearLayout.setDividerStroke()` for theme-aware dividers; also used by `com.pocket.sdk.util.view.tooltip.view.CaretTooltip` for bubble chrome. It consumes color-state-list resources and produces the current color for canvas draws.
## Key pieces
- `StatefulPaint(context, colorResourceId)` / `(res, colorResourceId)` / `()` — WHY: three ways to bind a state-list resource, or none for a manually supplied list.
- `setStatefulColor(color, state)` — WHY: swaps the whole color list at runtime (e.g. theme change) and immediately applies the current state. Usage in words: call when the theme flips, then redraw.
- `setState(state)` — WHY: the core lookup; resolves `getColorForState(state, TRANSPARENT)` and applies it, returning true only if the color actually changed.
- `hasColor()` — WHY: lets owners skip state work when no list was ever set.
## Junior notes
- Falls back to `TRANSPARENT` when no list matches; a mysteriously invisible shape usually means the state set has no entry in your color-state-list XML.
- `setColor()` on this class is overwritten by the next `setState()`; always change colors via `setStatefulColor`, never by calling `setColor` directly.

