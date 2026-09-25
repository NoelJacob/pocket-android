# Pocket/src/main/java/com/pocket/app/settings/beta/TCActivity.java
## What this is
This is the thin host activity for the internal beta/dev-config screen. It does nothing but place a BetaConfigFragment as its content on first creation and report its analytics view name as DEVCONFIG. Audio mini-player UI is disabled here since it is a utility screen, not content.
## How it fits
Launched from PrefsFragment's Alpha-only row (internal builds only) via an explicit Intent. TCActivity owns the window and analytics scope; BetaConfigFragment owns all rows and behavior. ActivityAccessRestriction.ANY means no login is required.
## Key pieces
- `onCreate`: setContentFragment(BetaConfigFragment) only when savedInstanceState is null — WHY the null check: on rotation the fragment is restored automatically and re-adding would stack a duplicate.
- `getActionViewName() (DEVCONFIG)` / `isListenUiEnabled() (false)`: analytics labeling and mini-player suppression.
## Junior notes
- Thin-activity pattern: when a fragment needs a full-screen phone container, pair it with an activity this small rather than stuffing logic here — all behavior stays in the fragment for dialog reuse.
