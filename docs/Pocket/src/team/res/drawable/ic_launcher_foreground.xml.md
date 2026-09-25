# Pocket/src/team/res/drawable/ic_launcher_foreground.xml

## What this is

The vector foreground layer of the team (alpha) build's adaptive launcher icon: the white Pocket pocket-and-checkmark glyph drawn in brand coral (`#EF4056`) with a dark `#333` detail path on a 108dp viewport. The system pairs it with a background layer and masks it (circle, squircle) per device.

## How it fits

Used by the `team` source set's launcher icon definition: the launcher renders this foreground over its background color whenever the "Pocket Dev" alpha app appears in the app drawer or recents. It is variant-specific on purpose, so internal builds are visually distinguishable from the production icon defined under `src/main`.
