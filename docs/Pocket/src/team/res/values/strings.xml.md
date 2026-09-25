# Pocket/src/team/res/values/strings.xml

## What this is

Three team-variant string overrides that rebrand internal builds as "Pocket Dev" and add an alpha-only debug option. All are `translatable="false"` because they are developer-facing, never localized.

## How it fits

The manifest overlay (`Pocket/src/team/AndroidManifest.xml`) reads `@string/nm_icon_alpha` from here for the app label, and settings/debug screens read the other two. Entry inventory: `nm_icon` ("Pocket Dev", launcher label), `nm_icon_setting` ("Pocket Dev Settings", settings header), `setting_background_sync_5` ("[Alpha only] Every 15 minutes", extra-frequent background-sync choice wired to the sync scheduler).
