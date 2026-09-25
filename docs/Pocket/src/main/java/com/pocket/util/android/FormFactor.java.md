# Pocket/src/main/java/com/pocket/util/android/FormFactor.java

## What this is
The app's "what kind of device / window is this?" oracle: it buckets the device into phone, large phone, small/micro tablet, or tablet, and answers layout questions from that. It solves responsive-layout branching — one- versus two-column lists, dialogs versus full screens, phone versus tablet pixel math. For example, `HomeFragment` asks `FormFactor.isTablet(context)` to pick its layout, and `ListenSettingsFragment` asks `showSecondaryScreensInDialogs(activity)` to decide between a dialog and a full Activity.

## How it fits
It is a static helper called from all over the UI layer: `HomeFragment`, `DetailsFragment`, `DetailsSkeletonView`, `SlatesSkeletonView`, `AuthenticationFragment` (onboarding page widths), `Help` (device label in diagnostics), `ListenSettingsFragment`, `DisplaySettingsManager`, and `PocketUiPlaygroundActivity`. It reads device configuration from `App` resources and per-window metrics from passed Activities/contexts. Screens branch on its answers to choose layouts, navigation modes, and sizes.

## Key pieces
- `get()` / `get(context)`: return the device bucket (PHONE, LARGE_PHONE, SMALL_TABLET, MICRO_TABLET, TABLET, UNKNOWN). WHY two forms: the cached no-arg form is cheap but app-global; the context form re-evaluates for the right resources.
- `isTablet(context)` / `isPhone()` / `isTabletLarge()` / `isTabletSmall()`: the everyday branches. WHY they exist: call sites should read intent ("is this a tablet?"), not compare bucket constants.
- `showSecondaryScreensInDialogs(context)`: true on tablets / small tablets. WHY it exists: one rule for the dialog-vs-activity navigation pattern.
- `dpToPx(phone, tablet)` / `dpToPx(dp)` / `dpToPxF(dp)` / `pxToDp(px)`: density conversions with a phone/tablet two-value form. WHY the pair form: many dimensions legitimately differ by form factor.
- `getWindowWidthPx(activity)` / `getWindowWidthDp(activity)`: live window width. WHY they exist: the modern, multi-window-safe replacement for cached screen sizes (see `ScreenWidth`).
- `getLabel()` / `getClassKey(nullPhone)`: human-readable device name (used in Help diagnostics) and analytics bucket key. WHY: consistent naming everywhere.
- `isKindleFire(only7Inch)` / `init()`: an Amazon-device quirk check and an eager cache initializer.

## Junior notes
- Context matters enormously here: use the application context for "what device is this" but an Activity/window context for "how big is this window" — an Activity's `screenLayout` reflects its window, not the device. The code comments call this out explicitly.
- The no-arg `get()` and `isTablet()` are `@Deprecated` and cached: they go stale under multi-window/resizable modes. Prefer the context-taking forms and `getWindowWidthDp/Px`.
- `dp` (density-independent pixels) is Android's unit for "same physical size on any screen"; these helpers convert to real pixels using cached display metrics.
