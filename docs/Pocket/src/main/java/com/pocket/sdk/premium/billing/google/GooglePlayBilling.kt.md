# Pocket/src/main/java/com/pocket/sdk/premium/billing/google/GooglePlayBilling.kt
## What this is
The raw Google Play Billing wrapper: connects to Play, loads offers, launches the Play purchase sheet, acknowledges purchases, and then activates them on Pocket's server via a `purchase` sync action. This is where the UI-to-Play-to-server chain actually happens.
## How it fits
Owned by `PremiumPurchaseHelper`, which passes its `ProductList`, host `Activity`, and itself as `PremiumBillingCallbacks`. It delegates loading to `LoadInventoryTask` downstream, and after a Play success it calls Pocket's sync API (`pocket().syncRemote(...)` with a `purchase` action) so the server grants premium. Results flow back up through the callbacks.
## Key pieces
- `getProductsAsync / connect()` — opens a Play connection (`BillingClient.startConnection`) and fires `LoadInventoryTask`; cached `products` are returned immediately on repeat calls. No-ops while already connected/connecting.
- `startPurchase(product)` — launches the Play purchase sheet (`launchBillingFlow`) for a `GooglePlayProduct`; records it as `pendingProduct` so the later purchase callback can be matched. Throws if handed a non-Play product.
- `updatePurchases(...)` — Play's purchase listener: ignores non-`PURCHASED` or already-acknowledged items, acknowledges the token (required by Play or the purchase is refunded), then calls `activatePurchase(..., NEW_PURCHASE)`.
- `activatePurchase(product, purchaseData, type)` — the Pocket-server step: sends `getuser` plus a `purchase` action (source `googleplay`, sku, micro-price, currency, Play receipt JSON, `purchase` vs `restore`, timestamp). Success means premium is truly granted; failure routes to `onProductPurchaseActivationFailed`.
- `createError(responseCode, ...)` — maps Play codes: `USER_CANCELED` → `CANCEL`, `ITEM_ALREADY_OWNED` → `ALREADY_PURCHASED`, anything else → `FATAL` if online else `TEMPORARY`.
- `onSaveInstanceState / disconnect()` — persists `pendingProduct` (a `Parcelable`, Android's passable-object mechanism) across rotation; `disconnect()` ends the Play connection.
## Junior notes
- Buying premium is two commits: Play charges (acknowledge within 3 days or it auto-refunds) and then Pocket's server records the receipt. Only `onProductPurchaseSuccess` means both done.
- `coroutines` are not used here; Play uses callback listeners, and the server sync uses `onSuccess/onFailure` listeners, so all of this runs off the UI thread with callbacks back to it.
- `pendingProduct` matching is fragile: the callback loop `continue`s past purchases that do not match it, so starting a purchase without loaded products silently does nothing.
