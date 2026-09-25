# Pocket/src/main/java/com/pocket/util/android/view/DialogSizeWrapper.java
## What this is
A `ThemedFrameLayout` (theme-aware frame container) that sizes dialogs to fit the screen: it caps width and height against display size minus a minimum padding, honoring `max_width` XML attributes and `dialog_max_width/height` dimens. It also paints the dialog box background and adjusts padding per light/dark theme state.
## How it fits
Used as the dialog container behind `AbsPocketFragment` dialog presentations (dialog-styled secondary screens on tablets) and any layout inflating this view; ` pocket ` `attrs.xml` declares its `max_width` styleable. It reads screen size via `WindowManagerUtil` and background styling via `ButtonBoxDrawable`, producing the measured dialog frame downstream layouts sit inside.
## Key pieces
- `DialogSizeWrapper(context)` / `(context, attrs)` — WHY: code vs XML construction; the XML path reads `max_width` and applies the box background.
- `onMeasure(...)` — WHY: the core clamp; shrinks requested width/height to `min(screen - padding, max)` before measuring children. Usage in words: wrap dialog content in this layout and it will never overflow the screen.
- `measure(specMode, specSize, max, capToMax)` — WHY: respects `EXACTLY` (parent-dictated size) while capping `AT_MOST`/unspecified sizes.
- `setMaxWidth(dp)` / `setMaxHeight(height)` — WHY: runtime resizing; converts dp to px via `FormFactor` and re-lays out.
- `drawableStateChanged()` — WHY: theme reaction; removes padding in light mode, adds a 1dp hairline pad in dark mode.
## Junior notes
- `setMaxWidth` takes dp (density-independent pixels) but stores px; do not pre-convert before calling it.
- Height is capped against `maxWidth` in one line (`min(displayHeight - pad, maxWidth)`), which looks like a copy bug but is intentional-ish square-ish dialog behavior; check designs before "fixing" it.
- Reads `WindowManager` on every measure; cheap enough for dialogs, but do not reuse this as a general container in scrolling lists.

