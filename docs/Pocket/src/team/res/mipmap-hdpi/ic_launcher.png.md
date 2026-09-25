# Pocket/src/team/res/mipmap-hdpi/ic_launcher.png

## What this is

The team (alpha) build's launcher icon bitmap at hdpi density (~1.5x baseline, 72px bucket). A PNG (1,756 bytes) is used here because launcher icons are fixed raster assets the home screen reads directly.

## How it fits

Packaged from the `team` source set so alpha installs show the "Pocket Dev" icon; Android picks this density variant on hdpi screens, falling back to the sibling `mipmap-mdpi/xhdpi/xxhdpi/xxxhdpi` files on other screens. Provenance: checked-in build asset under `Pocket/src/team/res`; regenerated whenever the team icon art changes, never hand-edited as binary.
