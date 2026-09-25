# Pocket/src/main/java/com/pocket/app/settings/appicon/AppIconSettingsViewModel.kt
## What this is
This is the ViewModel for the app-icon picker. It snapshots the current launcher icon once, exposes the automatic icon and the full icon list as UI states, and switches the launcher icon when the user taps. There is intentionally no observable stream here — the screen reads plain vals computed at creation, and switching writes straight through to AppIcons.
## How it fits
Hilt provides it to AppIconSettingsFragment (constructor-injected AppIcons helper). The fragment reads automaticIcon/allIcons to render the grid; onAutomaticIconClick/onIconClick set AppIcons.current, which enables the chosen launcher activity-alias and disables the rest. The OS then shows the new icon on the home screen.
## Key pieces
- `currentIcon` (private snapshot): captured once so selected flags are computed against the icon active when the screen opened.
- `automaticIcon` / `allIcons`: mapped via toAppIconUiState() into drawable + label + selected triples for Compose rows.
- `onAutomaticIconClick()` / `onIconClick(index)`: the two writes — index maps positionally into AppIcons.all.
- `onViewShown()`: empty hook reserved for future seen-analytics; not dead code, just not wired yet.
## Junior notes
- Because state is a snapshot (not a Flow), returning to this screen rebuilds the ViewModel and re-snapshots — that is also why PrefsFragment's app-icon summary refreshes in onStart via rebuildPrefs.
- Icon switching via PackageManager component enable/disable requires the manifest activity-aliases to stay in sync with AppIcons.all; adding an icon means touching both.
