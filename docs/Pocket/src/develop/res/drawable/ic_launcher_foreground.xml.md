# Pocket/src/develop/res/drawable/ic_launcher_foreground.xml

## What this is

This is the develop-build variant of the launcher icon foreground: a vector drawable of the Pocket checkmark logo. A source set here means a per-build-variant resource folder whose files replace the `main` ones when that variant builds. Compared with the current `main` foreground it is an older revision, with a different checkmark path and an extra dark detail shape.

## How it fits

The `develop` source set overlays `main`: when a develop build compiles, this file replaces `main/res/drawable/ic_launcher_foreground.xml` and is composited by `develop/res/mipmap-anydpi-v26/ic_launcher.xml` over `@color/ic_launcher_background`. At runtime the system renders it as the foreground layer of the adaptive launcher icon. Note the main `build.gradle.kts` currently registers only the FDROID flavor, so this overlay may be legacy.
