# utils-android/src/main/java/nl/codesoup/cubicbezier/CubicBezierInterpolator.java

## What this is

This file implements `CubicBezierInterpolator`: a focused piece of the utils-android module. It is small on purpose; its name is the best guide to its job.

## How it fits

It is used through its public symbols by neighboring files in the same module; see Key pieces below for the exact entry points.

## Key pieces

- `CubicBezierInterpolator` (class, line 9) — Derived from: https://github.com/rdallasgray/bez
- `getInterpolation` (fun, line 37) — entry point other code calls; see callers for context.
- `getBezierCoordinateY` (fun, line 41) — entry point other code calls; see callers for context.
- `getXForTime` (fun, line 48) — entry point other code calls; see callers for context.
- `getXDerivate` (fun, line 61) — entry point other code calls; see callers for context.
- `getBezierCoordinateX` (fun, line 65) — entry point other code calls; see callers for context.
- `toString` (fun, line 73) — entry point other code calls; see callers for context.

Concrete endpoints referenced here:

- `https://github.com/rdallasgray/bez`

## Junior notes

- Leaf helper with no app state: keep it dependency-free and never let it import feature code, or every module pays for the cycle.
