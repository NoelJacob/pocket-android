# Pocket/src/main/java/com/pocket/app/premium/Premium.java

## What this is
The navigation helper for everything Premium (paid subscription): opening the upgrade screen, the renew screen, the purchase-complete message, and the right status page for the user's current state.

## How it fits
Injected as a `@Singleton` wherever an upsell can trigger — reader, list overflow, settings. It builds intents via `DeepLinks` (central deep-link router) or shows `PremiumSettingsFragment` directly; `PocketCache` (the account/subscription cache) answers whether the user is premium, paid, or upgrade-eligible.

## Key pieces
- `newPremiumDeeplink(context)` — WHY: builds the premium-settings deep-link intent for cases where the caller wants to route it themselves.
- `showUpgradeScreen(context, source)` — WHY: the standard upsell entry; `source` (a `CxtSource` analytics enum) records *where* the upsell was triggered.
- `showRenewScreen(context, source)` — WHY: re-entry for expired/expiring users; currently routes to the same settings destination.
- `showPremiumForUserState(activity, source)` — WHY: picks status-vs-purchase content based on `hasPremiumAndPaid()` / `isPremiumUpgradeAvailable()` (today all branches show the settings fragment — the branching is the seam for future states).
- `showPurchaseComplete(activity, source)` — WHY: the success confirmation, shown via `PremiumMessageActivity` with title/message/button strings.

## Junior notes
- Always pass a meaningful `CxtSource` — upsell conversion is measured per source, and a wrong/placeholder source corrupts that data.
- Login gating lives in the destination (`PremiumMessageActivity` requires login), so callers do not need their own auth check.
