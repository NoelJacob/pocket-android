# pocket-ui/src/main/java/com/pocket/ui/view/themed/ThemedLottieAnimationView.kt
## What this is
A Lottie animation view (plays After-Effects animations exported as JSON) that recolors itself for light/dark mode without shipping two animation files. Subclasses declare one animation asset plus the per-theme color swaps; the base class applies them whenever the theme state changes.

## How it fits
Themed animated illustrations extend this class, implementing `asset()` (the JSON file name) and `light()` / `dark()` (lists of ColorChange: key path plus color property plus replacement color). `drawableStateChanged()` reads the current theme state and calls `updateTheme()`, which registers Lottie value callbacks rewriting those color groups. `debugKeypaths()` logs every key path in the file to help find recolorable elements.

## Key pieces
- `asset()` — abstract; the Lottie JSON file to play, set in init.
- `light()` / `dark()` — abstract; the color swaps per theme, each a ColorChange of KeyPath plus Lottie property (usually STROKE_COLOR or COLOR) plus color int.
- `drawableStateChanged()` — watches the theme state and applies the matching swap list; the theming hook.
- `updateTheme()` — registers one value callback per ColorChange.
- `debugKeypaths()` / `onVisibilityChanged()` — when enabled, dumps all key paths to logcat once visible for debugging color targets.
- `postOnLayoutCompat()` — retries a runnable until the view has nonzero size, working around a Lottie layout bug where width/height stay zero after the first pass.

## Junior notes
- Shipping one JSON plus code-side recoloring saves roughly a whole animation file per themed illustration (tens of KB each).
- Subclasses must get KeyPaths right; turn on `debugKeypaths()` and read logcat rather than guessing paths.
