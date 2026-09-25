# pocket-ui/src/main/java/com/pocket/ui/util/IntrinsicSizeHelper.java

## What this is
A helper that gives a custom view a fixed intrinsic size (the natural size a view reports when its parent lets it choose, as with `wrap_content`) without writing measure-spec logic by hand. Construct it with the desired width/height in pixels, then run the parent's measure specs through it in `onMeasure`.

## How it fits
Fixed-size custom views (dots, badges, icons drawn at an exact diameter) hold one of these and call `applyWidth` / `applyHeight` on their measure specs before `super.onMeasure`. The parent layout then measures the view at the helper's size whenever it is allowed to choose.

## Key pieces
- Constructors `(diameter)` and `(width, height)`: the single-value form is shorthand for square views. Passing `-1` for a dimension means "no opinion", leaving that axis untouched.
- `applyWidth` / `applyHeight`: per-axis entry points so a view can fix one dimension and leave the other free.
- `applyDimension(measureSpec, defaultSize)`: the actual rule. `EXACTLY` (parent dictated a size) always wins and the spec passes through; `AT_MOST` (an upper bound) clamps the intrinsic size down to the bound; `UNSPECIFIED` (no constraint) uses the intrinsic size as-is. This precedence mirrors how Android expects wrap_content views to behave.

## Junior notes
- Values are raw pixels, so convert dp with `DimenUtil` before constructing. Passing dp numbers directly produces tiny views on high-density screens.
- `EXACTLY` always wins by design: if a layout or `match_parent` forces a size, the helper must not fight it, or the view will break its parent's layout.
