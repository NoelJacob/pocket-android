# Pocket/src/main/java/com/pocket/app/CustomTabs.kt
## What this is
Owns the "which browser opens links" setting. It enumerates installed browsers via the package manager, detects Custom Tabs support (Android's API for rendering a web page inside a lightweight app-owned sheet instead of jumping to the full browser), persists the user's pick in the `defaultBrowser` pref, and gates the whole setting behind a server kill switch. The login screen's Custom Tab launch is one consumer of this ecosystem.
## How it fits
The settings screen (`PrefsFragment`) reads `getBrowserOptions()` to show the picker and `isBrowserSettingEnabled()` to hide it when killed; `preferredBrowserPackageName`/`willShowChooser` tell link-opening code whether a browser is pinned or the system chooser should appear. Constructed by Hilt with the app context, server flags, and prefs.
## Key pieces
- `preferredBrowserPackageName`: stored package name, null meaning system default; WHY null-as-default is that it stays valid no matter what the user installs/uninstalls.
- `willShowChooser`: true when no browser is pinned; drives whether link taps show the disambiguation dialog.
- `isBrowserSettingEnabled`: suspend check of the `perm.android.app.disable_default_browser_setting` flag; WHY suspend is that flags load asynchronously.
- `getBrowserOptions`: queries `ACTION_VIEW/http` handlers plus the Custom Tabs service per package to build the option list; WHY per-package service resolution is that Custom Tabs support varies by browser and version.
- `BrowserOption` (`DefaultBrowser` / `InstalledBrowser` with `supportsCustomTabs`): closed set of choices; sealed interface keeps the picker exhaustive.
- `BrowserOptions` (`labels`, `selected`, `selectedLabel`, `onSelected`, `resetIfSelectionInvalid`, `pickPreferredBrowser`): picker model; `selected` falls back to Default when the stored package is gone, and `pickPreferredBrowser` prefers Chrome, then Firefox channels, then any Custom-Tabs-capable browser.
## Junior notes
- `queryIntentActivities` with `MATCH_ALL` can return non-browser handlers for a bare http intent; the list is presentational, so a stale entry is harmless because `resetIfSelectionInvalid` repairs the stored pick.
- Never persist a browser choice when the kill switch is on: the setting UI is hidden, and writing the pref behind its back would surprise `willShowChooser` logic.
