# Pocket/src/main/java/com/pocket/app/settings/premium/PremiumSettingsActivity.java
## What this is
This is the thin full-screen host for the Premium subscription screen on phones. It places a PremiumSettingsFragment as content on first creation and labels the screen PREMIUM_SETTINGS for analytics. Unlike the cache host, it requires login — premium details are meaningless for guests.
## How it fits
Launched from PrefsFragment's Premium row via PremiumSettingsFragment.show() (dialog on tablets, this activity on phones). All fetching and row-building lives in the fragment.
## Key pieces
- `startActivity` / `newStartIntent` + savedInstanceState-guarded setContentFragment: standard thin-host launch and rotation-safe attach.
- `getAccessType() (REQUIRES_LOGIN)`: WHY guests never land here — the activity refuses them before the fragment's network calls run.
## Junior notes
- Access restrictions live on the activity, not the fragment — when adding a gated screen, set the restriction here and mirror any row visibility in PrefsFragment.
