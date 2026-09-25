# Pocket/src/main/java/com/pocket/sdk/premium/billing/google/Products.kt
## What this is
The loaded offer set: the monthly and yearly `GooglePlayProduct`s to display, each carrying whether the user already owns it. Null monthly/yearly means that option is unavailable.
## How it fits
Created by `LoadInventoryTask` via `create(skus, available)`, enriched with `attachPurchases()`, then delivered through `GooglePlayBilling` → `PremiumPurchaseHelper` to the purchase screen, which shows `monthly`/`yearly` and uses `isPurchased()` for restore.
## Key pieces
- `monthly / yearly` — the current sellable products (Kotlin `var` with private setter: readable everywhere, set only inside this class).
- `create(skus, available)` — sorts Play results into monthly/yearly buckets via `ProductList`, marks the current-SKU ones as the display offers; returns null when neither bucket has a current offer (treated as load failure upstream).
- `attachPurchases(purchases)` — matches each owned Play purchase to a product by SKU id (`purchase.skus.firstOrNull()`) and stamps the receipt via `setPurchased()`; blank ids are skipped.
- `isValid` — true when at least one display offer exists; legacy-only results (no current SKU) are invalid.
## Junior notes
- Monthly and yearly are independent nullables; the UI must handle only-one-available (e.g. show just yearly) rather than assuming both exist.
- Matching uses the first SKU on the purchase; multi-SKU purchases only match their first id, so bundle SKUs would need extra handling.
