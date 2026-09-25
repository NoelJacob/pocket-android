# Pocket/src/main/java/com/pocket/sdk/premium/billing/google/Sku.java
## What this is
One sellable product across time: the current Play SKU id plus any legacy ids from past price changes. It answers "is this id mine?" and "is it the one we sell now?"
## How it fits
Assembled in `GoogleBillingUtilStrings` (e.g. yearly = v3 current plus v2/original legacy) and consumed by `ProductList`, which expands each `Sku` into its query list (`getAll()`) and its current check (`isCurrent()`).
## Key pieces
- `Sku(current, legacy...)` — first argument is the only id offered for new purchases; the rest are recognize-only for restore.
- `getAll()` — every id (current + legacy) as a `Set` (a collection with no duplicates); package-private, used when building the Play query.
- `isCurrent(sku)` — string-equality against the current id; decides what `Products.monthly/yearly` display.
## Junior notes
- Package-private on purpose: only the billing package should see SKU mechanics; UI code works with `Products`/`PremiumProduct` instead.
- In the Play Console each price change mints a new product id, which is why this class exists; forgetting to add the new id here makes the new price invisible to the app.
