# Pocket/src/main/res/values-night-v29/themes.xml

## What this is

This file defines 1 named styles/themes active for night mode: reusable bundles of colors, text appearances, and window flags so screens share one look.

## How it fits

Applied app-wide from `AndroidManifest.xml` (`android:theme`) and per-view via `style=`; this qualifier-specific file overrides `values/themes.xml` when night mode is on (Android picks the best-matching `values-<qualifier>/` file).

## Key pieces

- `Theme.PocketDefault`: named appearance/behavior bundle applied via `style=` or the app theme.
