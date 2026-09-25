# Pocket/src/main/java/com/pocket/sdk/premium/billing/PremiumBillingCallbacks.java
## What this is
The callback contract for the whole premium purchase flow. It reports product loading, purchase progress, and the two-stage success/failure of buying (Google Play side vs. Pocket-server activation side).
## How it fits
Implemented by `PremiumPurchaseHelper`, which forwards each event to the purchase screen (`PremiumPurchaseFragment` via `PurchaseListener`). Produced downstream by `GooglePlayBilling`, which invokes these as Google Play Billing and the Pocket sync server respond.
## Key pieces
- `onProductsLoaded / onProductsLoadError` — monthly/yearly offers are ready to display, or loading failed with a `PremiumBillingError`.
- `onProductPurchaseActivationStarted` — Google Play charged the user; the app is now telling Pocket's server (activation), so the UI should show a spinner.
- `onProductPurchaseSuccess` — both Google Play AND Pocket-server activation finished; premium can be unlocked.
- `onProductPurchaseFailed` — the Google Play step failed (cancel, already-owned, fatal).
- `onProductPurchaseActivationFailed` — Google Play succeeded but Pocket's server could not be notified; the user paid but premium is not yet active, so this needs retry/help UI.
## Junior notes
- Success has two steps on purpose: a Play purchase alone does not grant premium until Pocket's server records it via a `purchase` sync action.
- `ActivationFailed` is not a normal failure; the user may have been charged, so never treat it as "nothing happened".
