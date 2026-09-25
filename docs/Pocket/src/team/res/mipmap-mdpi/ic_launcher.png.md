# Pocket/src/team/res/mipmap-mdpi/ic_launcher.png

## What this is

The team (alpha) build's launcher icon bitmap at mdpi density (1x baseline, 48px bucket). A PNG (2,375 bytes) is used here because launcher icons are fixed raster assets the home screen reads directly.

## How it fits

Packaged from the `team` source set so alpha installs show the "Pocket Dev" icon; Android picks this density variant on mdpi screens, scaling to the sibling `mipmap-hdpi/xhdpi/xxhdpi/xxxhdpi` files elsewhere. Provenance: checked-in build asset under `Pocket/src/team/res`; regenerated whenever the team icon art changes, never hand-edited as binary.
