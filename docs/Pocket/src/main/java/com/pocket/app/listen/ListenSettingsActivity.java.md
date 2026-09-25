# Pocket/src/main/java/com/pocket/app/listen/ListenSettingsActivity.java

## What this is
A thin login-gated host Activity for the Listen (text-to-speech) settings screen: voice choice, engine, and playback options.

## How it fits
Launched via `startActivity(context)` from the player gear icon and elsewhere. On first creation it installs `ListenSettingsFragment` as its content; the fragment holds all real UI. It reports the `LISTEN_SETTINGS` analytics view and hides the global Listen mini-player while open so two players never overlap.

## Key pieces
- `startActivity(context)` — WHY: the single launch entry so callers never build the intent by hand.
- `onCreate` → `setContentFragment(...)` — WHY: installs the settings fragment only when there is no saved state; on rotation the system restores it automatically.
- `getAccessType() = REQUIRES_LOGIN` — WHY: voice settings are account-bound; logged-out users are redirected.
- `isListenUiEnabled() = false` — WHY: suppresses the floating Listen UI that every other activity shows.

## Junior notes
- `AbsPocketActivity` provides the Pocket theming/analytics shell — new settings screens should follow this Activity-plus-Fragment split rather than stuffing UI into the Activity.
- `getActionViewName` feeds screen-view analytics; renaming the Activity without updating it silently breaks funnels.
