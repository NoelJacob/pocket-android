# Pocket/src/main/java/com/pocket/sdk/premium/billing/google/GooglePlayProduct.java
## What this is
A `PremiumProduct` backed by Google Play's `SkuDetails` (price, title, description fetched from the Play Console listing), plus the purchase receipt once the user owns it. It is the object the purchase screen displays and buys.
## How it fits
Built by `LoadInventoryTask` from Play query results, grouped by `Products.create()`, and shown by the purchase screen. Passed to `GooglePlayBilling.startPurchase()`, which unwraps its `SkuDetails` to launch the Play sheet; its receipt (`purchaseData`) is later sent to Pocket's server in `activatePurchase()`.
## Key pieces
- `getSku / getType / getPriceMicros / getPriceCurrencyCode` — machine-readable fields pulled from `SkuDetails` for the server `purchase` action (micro-price = price × 1,000,000, avoiding float rounding).
- `setPurchased / isPurchased / getPurchaseData` — ownership flag plus the raw Play receipt JSON; null means not owned.
- `Parcelable` implementation (`writeToParcel`, `CREATOR`) — serializes the SKU JSON and receipt so the pending purchase survives screen rotation via `onSaveInstanceState`.
## Junior notes
- `Parcelable` is Android's fast object-passing mechanism for Bundles/Intents; here it exists purely for rotation survival, not for passing between screens.
- `CREATOR` throws `AssertionError` on bad parcel data; that means a corrupt saved-state Bundle crashes rather than silently selling the wrong item, which is intentional.
