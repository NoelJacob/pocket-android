# Pocket/src/develop/res/values/ic_launcher_background.xml

## What this is

This defines the `ic_launcher_background` color as white (`#FFFFFF`) for develop builds. It exists so the develop variant has its own background token to reference, independent of the shared palette.

## How it fits

The `develop` source set overlays `main`: this color is referenced by the develop adaptive icon (`develop/res/mipmap-anydpi-v26/ic_launcher.xml`), whereas the `main` icon points at `@color/white`. Change this value to re-tint the develop launcher background without touching production. At runtime it is only ever read as the icon background layer.
