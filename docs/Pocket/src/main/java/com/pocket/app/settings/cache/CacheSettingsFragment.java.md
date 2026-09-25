# Pocket/src/main/java/com/pocket/app/settings/cache/CacheSettingsFragment.java
## What this is
This is the offline-cache settings screen where users cap how much space downloads may use and which end of the list gets evicted first (newest or oldest). Edits go into temp prefs and only apply when the user taps Save — backing out with changes prompts a discard dialog. Saving writes the real Assets limits, runs the cleaner immediately, and fires analytics for whichever value changed.
## How it fits
Extends AbsPrefsFragment; shown via show() (dialog on tablets, CacheSettingsActivity on phones) from PrefsFragment. Rows are a header, a CacheLimitPreference (the CacheLimitSeekbar slider row) bound to sizeTemp, and a priority multiple-choice bound to sortTemp. onResume runs assets.clean() so users returning here see the effect of their last save.
## Key pieces
- `sizeTemp` / `sortTemp` (CACHE_SIZE_USER_LIMIT_TEMP, CACHE_SORT_TEMP): WHY temp prefs — the Save-button pattern needs somewhere to stage uncommitted edits; they are seeded from the real Assets values in onViewCreatedImpl.
- `CacheLimitPreference (inner Preference)`: non-clickable CACHE_LIMIT row type whose applyToView binds the slider to sizeTemp and updates the Save button + inline item-order label on every change.
- `hasChanges()` / `updateSaveButton()`: enables Save only when a temp differs from the real value.
- `confirmSaveChanges(isSave)` + `finish()` / `onBackPressed()` overrides: Save path shows a "may take a few minutes" confirm then save(); back/exit path shows a discard-or-continue dialog. mIsSaved prevents double-prompting after a committed save.
- `save()`: sends priority/limit analytics, calls assets.setCacheLimit, runs clean() now (not at session end), toasts confirmation, and finishes without re-prompting.
## Junior notes
- The app-bar Save button is grabbed via getActionView(0) after addButtonAction — positional, so do not add another app-bar button ahead of it without updating the index.
- PrefAdapter.notifyDataSetChanged on priority change re-binds the slider row to refresh its inline oldest/newest label — the two rows are intentionally coupled.
