# Pocket/src/main/java/com/pocket/util/android/animation/WebViewArgbEvaluator.java

## What this is
A per-channel color interpolator (blender between two ARGB colors) for property animations, deliberately matching how a WebView blends colors. It solves a visual-mismatch problem: Android's stock `ArgbEvaluator` converts through linear light space, while web content blends in plain sRGB, so animating a native view alongside Reader web content with the stock evaluator produces visibly different mid-colors. For example, `ThemeChange` uses this evaluator for its background and text-color transitions so the native Reader chrome and the article body shift hues identically.

## How it fits
It is a `TypeEvaluator<Integer>` plugged into color `ValueAnimator`s. Its known consumer is `ThemeChange`, which holds it as a shared `ARGB_EVALUATOR` constant for the day/night background and text transitions in the Reader. It takes start/end color ints plus a 0..1 fraction and returns the blended color; nothing else flows through it. (Copied from AOSP's evaluator with the gamma conversion removed.)

## Key pieces
- `evaluate(fraction, startValue, endValue)`: splits both colors into alpha/red/green/blue channels, linearly interpolates each, and recombines. WHY per-channel: that is exactly the web blending model, so native and web mids match frame by frame.

## Junior notes
- "sRGB vs linear" in one line: screens are nonlinear (gamma), so physically-correct blending converts to linear light first — but the web does not, and matching the web matters more here than physical correctness.
- Inputs and output are packed 32-bit ARGB ints (`0xAARRGGBB`); the bit-shifting unpacks each 0..255 channel, blends in 0..1 float space, and repacks with rounding.
- Use the stock `ArgbEvaluator` for normal native-only animations; reach for this one only when the animation must visually track web-rendered color.
