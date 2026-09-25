# Pocket/src/develop/res/mipmap-xxxhdpi/ic_launcher.webp

## What this is

This is the develop-build launcher icon raster for xxxhdpi screens (highest-density devices and launcher previews), in WebP format. It is a binary image, so its pixels are not documented here.

## How it fits

The `develop` source set overlays `main`: in develop builds this replaces the `main` xxxhdpi launcher image. At runtime, the launcher may use this highest-resolution copy for icon previews and on xxxhdpi devices without adaptive-icon support. Provenance: exported app-icon art checked in per density; the anydpi adaptive icon supersedes it on modern devices.
