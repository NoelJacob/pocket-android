# pocket-ui/src/main/java/com/pocket/ui/util/DimenUtil.kt

## What this is
A tiny converter between dp (density-independent pixels, the unit designers use so sizes look the same on every screen density) and px (raw screen pixels, what drawing code actually needs). It wraps `TypedValue.applyDimension` so call sites never hand-roll density math. Kotlin extension functions (functions callable as `16f.toPx(context)`) give the same conversions in a shorter form.

## How it fits
Any custom view or drawable doing manual measuring, drawing, or corner radii calls `dpToPx` / `dpToPxInt` to turn design specs into pixels. `AppBar`, `PaletteView`, and `BadgeDrawable` use it for icon sizes, corner radii, and stroke widths. The reverse `pxToDp` direction is for reporting pixel measurements back in dp.

## Key pieces
- `dpToPx(context, dp)`: the core conversion via `TypedValue.applyDimension`. Exists as the single correct path because some vendor skins scale `displayMetrics.density` directly, so dividing by `density` by hand gives wrong values on those devices.
- `dpToPxInt`: integer variant for APIs that need whole pixels (layout params, intrinsic sizes).
- `pxToDp` / `pxToDpInt`: reverse conversion, computed as `px / dpToPx(context, 1f)` so it inherits the same vendor-safe ratio.
- `Float.toPx` / `toDp` / `toPxInt` / `toDpInt`: Kotlin extensions that read naturally at call sites (`8f.toPx(context)`). Pure shorthand, no new behavior.

## Junior notes
- Always pass a `Context` whose resources match the screen being drawn (usually the view's own context); display metrics come from `context.resources`, so an application context with a different configuration can give subtly wrong values.
- Prefer the `Int` variants when the result feeds integer APIs to avoid float-rounding drift, but keep the `Float` variants for `Paint` and canvas drawing where sub-pixel precision matters.
