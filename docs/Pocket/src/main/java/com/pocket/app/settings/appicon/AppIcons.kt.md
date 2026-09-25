# Pocket/src/main/java/com/pocket/app/settings/appicon/AppIcons.kt
## What this is
This is the helper that actually changes Pocket's launcher icon. Android implements alternate icons as manifest activity-aliases (extra launcher entries, only one enabled at a time); this class lists the automatic entry plus Classic/Monochrome/Pride options, reads which alias is currently enabled via PackageManager, and switches by enabling one alias and disabling the others without killing the app.
## How it fits
Injected into AppIconSettingsViewModel (which renders the picker) and PrefsFragment (which shows the current icon's label as the row summary). current's getter resolves the enabled alias; its setter flips COMPONENT_ENABLED_STATE_ENABLED/DISABLED on each alias. loadIcon fetches each alias's drawable for the grid.
## Key pieces
- `automatic` / `all`: the ComponentName (package + activity-alias class) plus label/description for each icon — WHY ComponentNames are hard-coded strings: they must match the manifest aliases exactly.
- `current` getter: queries the enabled launcher activity and maps it back to an AppIconOption (first-match on class name).
- `current` setter + private setComponentEnabledSetting: enables the chosen alias, disables the rest, with DONT_KILL_APP so the switch does not restart Pocket.
- `loadIcon(option)`: loads the alias drawable with MATCH_DISABLED_COMPONENTS — required because disabled aliases are invisible to normal queries.
- `AppIconOption`: data triple of componentName, label string resource, and description.
## Junior notes
- `all.first { … }` throws if the enabled alias matches nothing (e.g. a manifest rename) — keep the alias list exhaustive and add new icons to both the manifest and this list together.
- DONT_KILL_APP means the launcher may take a moment to refresh the icon; that delay is normal OS behavior, not a bug.
