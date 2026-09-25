# Pocket/src/main/res/mipmap-anydpi-v26/ic_launcher_pride.xml

## What this is

This is the adaptive launcher icon definition for the Pride-edition app-icon variant (API 26+): Pride background plus foreground layers. It sits beside the standard `ic_launcher.xml`, not instead of it.

## How it fits

Selected at runtime when the user picks the Pride icon in the app-icon setting (`AppIconSettingsFragment`); the launcher then renders this instead of the standard icon. Density-specific `.webp` bitmaps under `mipmap-*/` cover the same variant on pre-8.0 launchers.
