# Pocket/src/main/java/com/pocket/sdk/premium/billing/PremiumPurchaseHelper.java
## What this is
The UI-facing orchestrator for buying premium. It wraps `GooglePlayBilling` (raw Play Billing calls), adds offline/already-subscribed guards, shows error dialogs with "Get Help" buttons, and translates everything into a simpler `PurchaseListener` for the purchase screen.
## How it fits
Created by the premium purchase screen (`PremiumPurchaseFragment`) with a `ProductList`, the host `Activity`, and a `PurchaseListener`. It owns a `GooglePlayBilling` instance downstream, and forwards Play/server events up as `PurchasingState` changes (`PURCHASING`, `RESTORING`, `ACTIVATING`, `IDLE`) and listener callbacks.
## Key pieces
- `getProductsAsync()` — loads offers; skips Play and reports offline if there is no network, remembers `FATAL` so later taps go straight to "Play unavailable".
- `startPurchase(product)` — guards (already premium? offline?) then sets state to `PURCHASING` and delegates to `GooglePlayBilling.startPurchase()`.
- `restorePurchase()` — for "Restore" taps: if products are not loaded yet it parks in `RESTORING` state and loads them, then re-enters itself; otherwise it looks for an already-purchased yearly then monthly product and activates it, or shows "no subscriptions found".
- `onProductsLoaded / onProductsLoadError` — caches `mProducts`; a load kicked off by screen-open (not a button tap) fails silently so the user can retry manually.
- `onProductPurchaseFailed` — `ALREADY_PURCHASED` routes to restore, `CANCEL` just resets, anything else shows the generic purchase-error dialog.
- `onProductPurchaseActivationFailed` — shows the activation-error dialog linking to the help article; dismiss notifies the listener to close the screen.
- `onSaveInstanceState / onDestroy` — lifecycle forwarding (a pattern where the screen passes its lifecycle events through): the pending Play purchase survives rotation, and the Play connection is closed when done.
## Junior notes
- Premium here gates paid features (ad-free, permanent library, premium fonts); purchase means a Google Play subscription, and activation means Pocket's server records it so all devices see premium.
- Callers MUST forward `onSaveInstanceState` and `onDestroy`, or a rotation during the Play sheet can lose the pending purchase and the Play connection leaks.
- `restore()` tries yearly before monthly; a user owning both activates yearly, which is the intended precedence.
