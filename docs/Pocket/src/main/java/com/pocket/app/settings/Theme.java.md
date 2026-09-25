# Pocket/src/main/java/com/pocket/app/settings/Theme.java
## What this is
This is the central store for Pocket's light/dark theme choice. It persists one int (LIGHT or DARK) in preferences, broadcasts changes through an RxJava Observable (a reactive stream observers subscribe to), and resolves the effective theme per screen — honoring per-Activity/Fragment flags that force light-only or dark-only. It also hands out matching background, status-bar, and navigation-divider colors plus drawable-state arrays for themed views.
## How it fits
A Hilt singleton injected anywhere a theme decision is needed; SystemDarkTheme writes into it when following the OS, PrefsFragment's theme picker writes via pref() + set(), and activities/views read via get(context/view) or observeFor(context). Themed views use getState() (STATE_LIGHT/STATE_DARK drawable states) to switch drawables.
## Key pieces
- `LIGHT` / `DARK` + `FLAG_ALLOW_ALL` / `FLAG_ONLY_DARK` / `FLAG_ONLY_LIGHT`: the stored value vs per-screen allow-list; applyFlagsToTheme forces the value when a screen disallows one mode.
- `get()` / `get(Context)` / `get(View, Fragment)`: WHY three overloads — raw stored value, activity-aware resolution, and fragment-aware resolution (PageFragments can force a mode); edit-mode views always report LIGHT for the layout preview.
- `set(theme)`: persists and pushes on the changes subject so observeFor subscribers re-render.
- `observeFor(context)`: maps change events to the resolved theme with distinctUntilChanged — subscribers only wake on real flips.
- Color helpers (getThemeBGColor, getStatusBarColor, getNavigationBarDividerColor) and `isDark(...)` overloads: keep every surface consistent with the resolved theme.
- `pref()`: exposes the raw IntPreference ONLY for binding the settings multiple-choice row — all other code must use get/set.
## Junior notes
- RxJava Observable here (not StateFlow) for historical reasons; subscribe in onStart and dispose in onStop like AbsPrefsFragment does, or the observer leaks.
- The View+Fragment lookup walks up to the parent fragment — a view created but not yet attached falls back to the context resolution, so themed custom views should refresh drawable state after attach.
