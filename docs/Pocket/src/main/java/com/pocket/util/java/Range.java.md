# Pocket/src/main/java/com/pocket/util/java/Range.java
## What this is
A mutable integer `min`/`max` pair plus static clamp and interpolation helpers for ints and floats. `limit()` clamps a value into bounds, `contains()`/`isWithin()` test membership, and `percentBetween()` maps 0..1 onto a span.
For example, a font-size control builds `Range(sizes[0], sizes[last])` and calls `range.limit(pref)` so a stored size never escapes the available steps.

## How it fits
Used by `DisplaySettingsManager` for reader display settings: `FONT_SIZE_RANGE`, `LINE_HEIGHT_RANGE`, and `MARGIN_RANGE` bound the font-size, line-height, and margin arrays, and `isPrefAtMax`/`isPrefAtMin` plus increment/decrement logic read `range.min`/`range.max` directly.

## Key pieces
- `min` / `max` fields plus `set(min, max)`: the bounds themselves. WHY they exist: a passed-around, mutable window rather than two loose ints.
- `limit(value)` (instance) and `limit(min, max, value)` (static int/float): clamp into range. WHY they exist: keep prefs, sizes, and animation values inside valid bounds.
- `contains(value)` / `isWithin(min, max, value)`: bounds tests. WHY they exist: readable "can we step further" checks.
- `percentBetween(percent, min, max)`: `min + percent * (max - min)`. WHY it exists: converts a 0..1 progress (e.g. a slider) into a value on a span.

## Junior notes
- The instance is mutable and not thread-safe; share a `Range` across threads only if it is effectively frozen after construction.
- Note the argument order on the static `limit(min, max, value)`; the value comes last, unlike Kotlin's `coerceIn`, so read call sites carefully.
