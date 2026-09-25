# Pocket/src/main/res/mipmap-anydpi-v26/ic_launcher.xml

## What this is

This is the adaptive launcher icon definition for API 26+: it composes the `@color/white` background with the `@drawable/ic_launcher_foreground` artwork (plus a monochrome layer for themed icons). The `anydpi-v26` qualifier means Android prefers this over every density-specific bitmap on modern devices.

## How it fits

The launcher renders this instead of the `mipmap-*/ic_launcher.png` bitmaps on Android 8.0 and above; those PNGs remain only for older launchers. The pride and black-and-white variants beside it are user-selectable via the app-icon setting (`AppIconSettingsFragment`).

## Key pieces

- `background` (`@color/white`): solid backdrop layer of the adaptive icon.
- `foreground` (`@drawable/ic_launcher_foreground`): Pocket wordmark art cropped to the launcher mask.
- `monochrome` (`@drawable/ic_launcher_foreground`): single-color layer used for Android 13+ themed icons.
