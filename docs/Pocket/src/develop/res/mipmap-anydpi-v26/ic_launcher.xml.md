# Pocket/src/develop/res/mipmap-anydpi-v26/ic_launcher.xml

## What this is

This is the develop-build adaptive launcher icon definition. An adaptive icon is Android's two-layer launcher icon (background plus foreground) that the system can mask into different shapes. It layers `@drawable/ic_launcher_foreground` over `@color/ic_launcher_background`, and reuses the foreground for the monochrome (themed-icon) layer.

## How it fits

The `develop` source set overlays `main`: this file replaces `main/res/mipmap-anydpi-v26/ic_launcher.xml` in develop builds. The visible difference from `main` is the background reference, `@color/ic_launcher_background` (white) instead of `main`'s `@color/white`, so the two can diverge per variant. At runtime the launcher reads it as the app icon for API 26 and above; older devices fall back to the `mipmap-<density>` rasters.
