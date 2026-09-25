# Pocket/src/main/java/com/pocket/sdk/premium/billing/google/LoadInventoryTask.kt
## What this is
The two-step Play inventory loader: first it fetches offer details (prices, titles) for the app's SKUs, then it fetches the user's existing purchases and stamps them onto those offers. It reports one combined result.
## How it fits
Fired by `GooglePlayBilling.connect()` after the Play connection opens. It queries Play via `querySkuDetailsAsync` then `queryPurchasesAsync`, builds a `Products` via `Products.create()` + `attachPurchases()`, and returns through `LoadInventoryCallbacks` to `GooglePlayBilling`, which forwards to the UI.
## Key pieces
- `execute / loadAvailableProducts()` — asks Play for `SkuDetails` on `skus.all`; wraps each result in a `GooglePlayProduct`. If `Products.create()` returns null (no current monthly/yearly found), the whole load fails.
- `loadCompletedPurchases(products, onLoaded)` — asks Play for owned `SUBS` purchases and calls `products.attachPurchases()` so restore can find them.
- `postUiOnComplete / uiOnComplete` — hops back to the UI thread (`runOrPostOnUiThread`, i.e. schedule on Android's main thread) before invoking callbacks, since Play callbacks arrive on a background thread.
- `LoadInventoryCallbacks` — `onInventoryLoaded(products)` on full success, `onInventoryLoadError(responseCode)` with a raw Play code otherwise.
## Junior notes
- This is a plain class with an `execute()` method, not an Android `AsyncTask`, despite the name; there is no task cancellation to manage.
- A Play `OK` with zero matching SKUs is still a failure (null `Products` → `ERROR`); that usually means the SKU strings drifted from the Play Console listing.
