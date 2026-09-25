# Pocket/src/main/java/com/pocket/app/premium/PremiumPurchaseActivity.java
## What this is
This is the host screen (an Activity, the full-window entry point Android launches via an Intent) for the old Premium purchase flow. It does almost nothing except create and hold a `PremiumPurchaseFragment`, forward the analytics screen name to it, and lock the screen to light theme with login required. It is the navigation target other parts of the app open when they want to sell Premium.
## How it fits
Callers anywhere in the app call the static `startActivity(context, startSource, isRenew)` helpers, which pack the analytics source and renew flag into the Intent. `onCreate` unpacks them into `PremiumPurchaseFragment.newInstance(...)` and installs it via `setContentFragment`; the fragment then owns all UI and billing, and `getActionViewName()` simply delegates to the fragment for analytics.
## Key pieces
- `startActivity / newStartIntent` — static entry points that bundle `EXTRA_START_SOURCE` (a `CxtSource` analytics enum) and `EXTRA_RENEW` into the Intent. This is the only way the screen should be launched, so analytics context is never missing.
- `onCreate` — creates the fragment fresh on first launch, or re-finds it by tag after rotation. Exists to avoid stacking two purchase fragments on top of each other.
- `getAccessType() = REQUIRES_LOGIN` — guests are blocked before they ever see prices; `getDefaultThemeFlag() = FLAG_ONLY_LIGHT` keeps the paywall on a fixed light theme; `checkClipboardForUrl()` is disabled so opening this screen never triggers the "save this URL?" prompt.
## Junior notes
- `AbsPocketActivity` is the app's base Activity with login gating, theming, and analytics hooks — overriding those three small methods is how a screen opts into that behavior.
- `Parceller` is a helper for putting enums/lists into Intents and Bundles; plain `putExtra` cannot carry them directly.
