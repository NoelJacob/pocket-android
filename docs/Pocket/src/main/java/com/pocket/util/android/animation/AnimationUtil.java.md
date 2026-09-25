# Pocket/src/main/java/com/pocket/util/android/animation/AnimationUtil.java

## What this is
A frame-math helper for hand-rolled time-based animations: given a start time and duration, it reports how far along the animation is. It solves the "I need progress, loop count, and percentage for a custom drawable animation" problem without pulling in the full animator framework. For example, `RainbowDrawable` (the loading rainbow) calls `getAnimationValue(...)` every draw to decide which animation state and color to render.

## How it fits
Its known consumer is `RainbowDrawable`, which keeps an `AnimationValues` struct plus start/duration fields and re-queries them on each `invalidateSelf()` pass. It reads the clock via `SystemClock.uptimeMillis()` and writes plain fields back — no listeners, no downstream objects.

## Key pieces
- `AnimationValues` (fields `elapsedTotal`, `repeatCount`, `currentElapsed`, `currentPercent`): the reusable output struct. WHY a struct and not a return value: one allocation-free call fills in total time, loop count, in-loop time, and 0..1 progress together.
- `getAnimationValue(values, start, duration)`: fills the struct from the current uptime. WHY static with an out-param: drawable draw paths run per frame, so avoiding allocation matters.

## Junior notes
- Time base is `uptimeMillis` (time since boot, excluding deep sleep) — the standard Android animation clock. Wall-clock time would jump on timezone/NTP changes; this does not.
- `repeatCount` is total-elapsed divided by duration, floored: callers like `RainbowDrawable` use it to flip between STARTING and looping states.
- For ordinary view animations prefer `ValueAnimator`/`ObjectAnimator`; this helper is for custom `Drawable` rendering where the animator framework does not fit.
