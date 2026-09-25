# Pocket/src/main/res/values-v23/themes.xml

## What this is

This file defines 2 named styles/themes active for API v23: reusable bundles of colors, text appearances, and window flags so screens share one look.

## How it fits

Applied app-wide from `AndroidManifest.xml` (`android:theme`) and per-view via `style=`; this qualifier-specific file overrides `values/themes.xml` when running on that API level (Android picks the best-matching `values-<qualifier>/` file).

## Key pieces

- `Theme.PocketBase.Dark`: named appearance/behavior bundle applied via `style=` or the app theme.
- `Theme.PocketBase.Light`: named appearance/behavior bundle applied via `style=` or the app theme.
