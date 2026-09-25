# Pocket/src/main/res/values/themes.xml

## What this is

This file defines 9 named styles/themes: reusable bundles of colors, text appearances, and window flags so screens share one look.

## How it fits

Applied app-wide from `AndroidManifest.xml` (`android:theme`) and per-view via `style=`; variant files in `values-night-v29/` and `values-v23/v28/` override these for dark mode and newer APIs.

## Key pieces

- `Theme.PocketDefault`: named appearance/behavior bundle applied via `style=` or the app theme.
- `Theme.PocketBase.Dark`: named appearance/behavior bundle applied via `style=` or the app theme.
- `Theme.PocketBase.Light`: named appearance/behavior bundle applied via `style=` or the app theme.
- `Theme.PocketDefault.Light`: named appearance/behavior bundle applied via `style=` or the app theme.
- `Theme.PocketDefault.Dark`: named appearance/behavior bundle applied via `style=` or the app theme.
- `Theme.PocketDefault.Light.Transparent`: named appearance/behavior bundle applied via `style=` or the app theme.
- `Theme.PocketDefault.Light.Transparent.NoAnimation`: named appearance/behavior bundle applied via `style=` or the app theme.
- `LightPopupMenu`: named appearance/behavior bundle applied via `style=` or the app theme.
- `DarkPopupMenu`: named appearance/behavior bundle applied via `style=` or the app theme.
