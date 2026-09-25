# pocket-ui/src/main/java/com/pocket/ui/PocketDimensions.kt

## What this is

A small Compose-only spacing helper. It exposes the standard Pocket gaps (`spaceSmall/Medium/Large`, backed by `pkt_space_*` dimension resources) as `Dp` values (density-independent pixels, the unit Compose layouts use). It also exposes `sideGrid`, an adaptive side margin that returns the large gap on wide screens and the medium gap on phones.

## How it fits

Compose screens and components read these values instead of hardcoding padding — e.g. a list screen uses `PocketDimensions.sideGrid` for its horizontal content padding so tablets automatically get wider margins. The plain `space*` values come from `R.dimen.pkt_space_*`, so designers tune spacing in one XML file. `sideGrid` reads `LocalConfiguration.current` (the device's current screen configuration, provided by Compose) at composition time.

## Key pieces

- `spaceSmall / spaceMedium / spaceLarge` — WHY: single source of truth for spacing tiers; each is a `@Composable` getter (a property that can read Compose state/resources) wrapping `dimensionResource(...)`.
- `sideGrid` — WHY: one adaptive margin for phone-vs-tablet layouts; checks `configuration.smallestScreenWidthDp >= LargeScreenSmallestWidth` (590dp, the standard large-screen breakpoint) and picks the large or medium gap.
- `LargeScreenSmallestWidth = 590` — WHY: named threshold so the tablet cutoff is explicit and changeable in one place.

## Junior notes

- These getters are `@Composable`, so they can only be called from inside a composable function — not from a ViewModel or plain helper.
- `dimensionResource` re-reads on configuration change (rotation, font scale), so callers automatically get updated values on recomposition.
