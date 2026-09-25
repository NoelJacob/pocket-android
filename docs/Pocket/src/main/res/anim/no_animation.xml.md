# Pocket/src/main/res/anim/no_animation.xml

## What this is

Animation resource: Empty animation set used to disable transition animations where the navigator still requires an anim resource.

## How it fits

Loaded as `R.anim.no_animation` (e.g. fragment-transition overrides or `overridePendingTransition`); an empty `<set>` like this one intentionally plays no animation where the API still demands a resource.
