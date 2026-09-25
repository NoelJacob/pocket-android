# Pocket/src/main/java/com/pocket/app/settings/beta/BetaConfigFragment.java
## What this is
This is the internal-only "dev config" settings screen for employees and beta testers. It exposes tools no real user should see: Unleash feature-toggle debugger, LeakCanary, API/parser/Snowplow server overrides, push-token and UID copy buttons, premium-state spoofing, fake user messages, and transplant/debug utilities. Every row is gated on internal company builds, and most server changes require an app restart.
## How it fits
Hosted by TCActivity and reachable from PrefsFragment's Alpha-only section (itself visible only on internal builds); onAttach crashes unless hosted by TCActivity on an internal build, so it cannot leak into production navigation. Rows launch UnleashDebugActivity, tweak PocketServer.DevConfig prefs, or fire sync actions; requireRestart() prompts for the restart that server-URL changes need.
## Key pieces
- `createPrefs(prefs)`: early-returns on non-internal builds — WHY the screen is blank rather than hidden if ever mislaunched.
- Server-override rows (Api, Parser, Snowplow): multiple-choice prefs with custom-URL dialogs that write DevConfig values; choosing custom returns false to keep the dialog open until a valid host is entered.
- Diagnostics rows (FCM token truncated display, UID copy, Unleash toggles, LeakCanary gated on dev builds): copy-to-clipboard and activity launches for support/debugging.
- Premium/message spoof rows: fake premium status, user-message injection, and transplant helpers used to reproduce QA scenarios.
## Junior notes
- Never link production UI to anything here — the isForInternalCompanyOnly gate is a build-flavor check, and rows assume debug backends that do not exist in release.
- DevConfig URL changes only take effect after restart because the HTTP stacks are built at app init; the requireRestart prompt is load-bearing, not cosmetic.
