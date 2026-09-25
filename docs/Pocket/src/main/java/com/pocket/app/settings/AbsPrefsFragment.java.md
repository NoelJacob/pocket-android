# Pocket/src/main/java/com/pocket/app/settings/AbsPrefsFragment.java
## What this is
This is the base class for every settings screen in the app. It inflates the shared settings layout (app bar + loading view + RecyclerView list), asks subclasses for their rows via createPrefs(), and renders them through an inner PrefAdapter that maps each Preference type to its row view. It also handles the loading/error/empty states and auto-refreshes rows when any preference changes.
## How it fits
PrefsFragment, CacheSettingsFragment, PremiumSettingsFragment, BetaConfigFragment, and AccountManagementFragment all extend this; each only implements getTitle(), getBannerView(), and createPrefs(). The fragment is hosted full-screen or as a dialog depending on FormFactor; finish() closes whichever container it is in. Preference taps that navigate (e.g. Premium row → PremiumSettingsFragment.show) are defined in the subclass's createPrefs.
## Key pieces
- `createPrefs(prefs)` (abstract): WHY subclasses are tiny — they just append headers, toggles, actions, and multiple-choice rows to the list.
- `onViewCreatedImpl(...)`: inflates activity_settings, binds the app bar title/back, creates the adapter, and calls createPrefs once.
- `rebuildPrefs()`: clears and re-runs createPrefs plus notifyDataSetChanged — call after any external change (login state, storage type, fetched subscription).
- `PrefAdapter` / `onPreferenceChange(forceViewUpdate)`: RecyclerView adapter over heterogeneous row types; subscribes to prefs().changes() in onStart so edits from elsewhere refresh visible rows; disposed in onStop.
- `showProgress()` / `hideProgress()` / `showError(e, retry)`: loading overlay, list reveal, and error-with-retry states (long-pressing retry offers to file an error report).
## Junior notes
- AbsPocketFragment splits inflation (onCreateViewImpl) from view setup (onViewCreatedImpl) — put findViewById work in the latter, and always null fields in onDestroyView to avoid leaking the old view hierarchy.
- rebuildPrefs is safe to call any time but no-ops when detached/finishing; never touch prefAdapter directly from subclasses — mutate via rebuildPrefs.
