# Pocket/src/main/java/com/pocket/sdk/premium/billing/google/GoogleBillingUtilStrings.java
## What this is
The registry of Google Play subscription SKU strings (product ids) the app knows about, grouped into `Sku` objects with current plus legacy ids. Changing subscription prices in the Play Console forces a new SKU, so old ids linger here for restore checks.
## How it fits
Referenced only by `GoogleBillingUtil` to build the `PREMIUM` and `AD_FREE` product lists. The strings flow into `LoadInventoryTask`, which asks Google Play for details on each one.
## Key pieces
- `SKU_MONTHLY` — current `pocket.premium.1month.v3` plus legacy `pocket.premium.1month`.
- `SKU_YEARLY` — current `pocket.premium.1year.v3` plus legacy `v2` and original ids.
- `SKU_AD_FREE_MONTHLY / SKU_AD_FREE_YEARLY` — the cheaper tier, no legacy ids yet.
## Junior notes
- Never remove a legacy SKU: a user who bought years ago still owns that id, and restore (`Products.attachPurchases`) matches on it.
- The "current" (first) id is the only one ever offered for new purchases; the rest are recognize-only.
