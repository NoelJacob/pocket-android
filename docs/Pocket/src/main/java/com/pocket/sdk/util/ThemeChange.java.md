# Pocket/src/main/java/com/pocket/sdk/util/ThemeChange.java
## What this is
A custom Android Transition (animation blueprint) that smoothly cross-fades background and text colors when the app theme changes. It captures each view's colors before and after, then animates between them over 300ms.
## How it fits
Triggered by AbsPocketActivity when the user switches theme (light/dark/sepia). The transition framework calls captureStartValues/captureEndValues, then createAnimator produces per-view color animators that run together across the screen.
## Key pieces
- `captureValues` (via captureStartValues/captureEndValues): records background drawable color and TextView text color under pocket:themeChange keys, including current-state drawable colors.
- `createAnimator`: builds ObjectAnimators on BackgroundColorProperty and TextColorProperty using an ARGB evaluator, so both plain and state-list drawables transition.
- `BackgroundColorProperty` / `TextColorProperty`: Property adapters letting ObjectAnimator drive setBackgroundColor/setTextColor as integers.
## Junior notes
- Transition framework = capture start scene, capture end scene, animate the differences. DURATION is a fixed 300ms; the WebViewArgbEvaluator handles color interpolation consistently including WebViews.
