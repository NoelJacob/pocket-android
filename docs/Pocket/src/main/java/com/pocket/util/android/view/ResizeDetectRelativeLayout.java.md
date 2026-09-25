# Pocket/src/main/java/com/pocket/util/android/view/ResizeDetectRelativeLayout.java
## What this is
A `RelativeLayout` (a view group positioning children relative to each other) with resize callbacks, a foreground overlay, max-width and max-height caps, and Pocket theme-state merging. It is the sibling of `ResizeDetectLinearLayout` for free-form rather than stacked layouts.
## How it fits
Used as `PocketActivityRootView`'s base-adjacent container and inside `CaretTooltip` and `res/layout/view_storage_location_option.xml` rows; `AbsPocketFragment` dialog plumbing and the `PocketTheme_maxHeight` attribute feed its sizing. It composes `ForegroundDrawableHelper` and `MaxWidthHelper` exactly like its linear sibling, plus a `maxHeight` clamp and an optional `AbsPocketFragment` reference for theme state.
## Key pieces
- Constructors — WHY: each resolves `MaxWidthHelper` against its style attrs; the XML constructor additionally reads `PocketTheme_maxHeight`.
- `setForegroundDrawable(...)` + foreground delegation (`dispatchDraw`, `verifyDrawable`, `jumpDrawablesToCurrentState`, `onTouchEvent`, `drawableStateChanged`) — WHY: same overlay contract as the linear variant; forward all of them.
- `onSizeChanged(...)` / `setOnResizeListener(...)` — WHY: resize events plus foreground bounds sync.
- `setFrag(frag)` — WHY: gives theme-state resolution fragment scope so the right theme overlay applies.
- `onCreateDrawableState(...)` — WHY: merges `App.theme().getState(this, mFrag)` so light/dark selectors work.
- `onMeasure(...)` — WHY: applies the max-width helper then the max-height clamp before measuring children.
## Junior notes
- `maxHeight` is only read in the `(context, attrs)` constructor; the three-arg and four-arg constructors skip it, so inflating with a style but no explicit handling can silently drop the cap.
- `mFrag` is optional and null-safe; without it the theme state falls back to the view-only lookup.
- Like its sibling, all foreground forwards are null-safe and must all be kept; dropping `verifyDrawable` breaks overlay invalidation.

