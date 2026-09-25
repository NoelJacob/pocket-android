# Pocket/src/main/java/com/pocket/util/android/ScreenWidth.java

## What this is
A deprecated cache of the device's shortest and longest screen widths (in pixels or dp) for both orientations. It solved the old problem of sizing full-bleed Reader and article images to the display. For example, `DisplaySettingsManager.getImageWidth()` still reads `ScreenWidth.get(activity).getLongest(false)` for phone image widths. It is deprecated because cached screen sizes go stale under multi-window and resizable modes.

## How it fits
Its known consumer is `DisplaySettingsManager.getImageWidth()`, which uses the longest width for article image sizing (combined with `FormFactor.isPhone()`). It measured the display once per Activity via `Display.getSize()` and cached the singleton, with `clearCache()` for invalidation. New code must use `FormFactor.getWindowWidthPx/Dp(activity)` instead, which queries the live window.

## Key pieces
- `get(activity)`: returns the cached measurement, computing it on first use. WHY it exists: measuring the display on every call was considered wasteful at the time.
- `getShortest(dp)` / `getLongest(dp)`: the short/long dimensions in pixels or dp (density-independent pixels). WHY the pair: layouts need "the narrow side" and "the wide side" regardless of current rotation.
- `getWidths(activity, display, point)` / `figureOutLongShort(displaySize)`: the measurement internals (subtracting decorations, ordering long vs short). WHY they exist: "available to the app" width excludes system UI, and orientation must not change which value is "longest".
- `clearCache()`: drops the cached singleton. WHY it exists: the escape hatch when the display actually changes.

## Junior notes
- Deprecated means "do not use in new code": the cache assumes one fixed screen, which breaks with split-screen, foldables, and freeform windows.
- The `dp` flag converts via display density; image-sizing code generally wants pixels (`false`), layout code wants dp (`true`) — match the flag to the consumer.
- The singleton is static state: in tests or multi-activity flows a stale `mScreenSize` silently serves the wrong display, which is exactly the bug class that got this class deprecated.
