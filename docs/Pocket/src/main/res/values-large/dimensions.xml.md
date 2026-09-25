# Pocket/src/main/res/values-large/dimensions.xml

## What this is

This file fixes shared sizes (3 dimensions for large screens): dialog bounds, player heights, card widths, chip metrics. Centralizing them keeps spacing consistent and makes tablet overrides one-file changes.

## How it fits

Layouts and styles consume them as `@dimen/<name>`; these override `values/dimensions.xml` on matching screens (smallest-width / large qualifiers win by Android's resource precedence).

## Key pieces

- `@dimen/rainbow_bar_height` (4dp): fixed size used by layouts/styles.
- `@dimen/saves_image_width` (105dp): fixed size used by layouts/styles.
- `@dimen/saves_image_height` (70dp): fixed size used by layouts/styles.
