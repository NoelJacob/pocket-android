# Pocket/src/main/java/com/pocket/sdk/premium/billing/google/GoogleBillingUtil.java
## What this is
Defines the two purchasable product sets: `PREMIUM` (full premium monthly/yearly) and `AD_FREE` (cheaper ad-free tier). Each lists the current sellable SKU (a product id string registered in the Play Console) plus legacy/alternative SKUs that count as owned.
## How it fits
The purchase screen picks one of these `ProductList`s and hands it to `PremiumPurchaseHelper` → `GooglePlayBilling` → `LoadInventoryTask`, which queries Google Play for exactly these SKU strings.
## Key pieces
- `TYPE` — `BillingClient.ProductType.SUBS`: both products are subscriptions, not one-time purchases.
- `PREMIUM` — sells `SKU_MONTHLY`/`SKU_YEARLY` but also recognizes the ad-free SKUs as valid alternatives (so ad-free buyers are treated leniently).
- `AD_FREE` — the mirror: sells the ad-free SKUs, recognizes full-premium SKUs as alternatives.
## Junior notes
- Premium gates paid features; the SKU string (e.g. `pocket.premium.1month.v3`) is the id you see in the Google Play Console, and the app must query the exact string.
- Two lists exist because the app has sold different tiers/names over time; adding a new price means adding a new `Sku` entry in `GoogleBillingUtilStrings`, not editing this file's shape.
