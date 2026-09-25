# Pocket/src/main/java/com/pocket/sdk/premium/billing/google/ProductList.java
## What this is
The app's definition of what it wants to sell and recognize: the monthly/yearly SKUs to offer plus every legacy/alternative SKU id that counts as owned. It answers "is this Play id monthly, yearly, or currently sellable?"
## How it fits
Built in `GoogleBillingUtil` (`PREMIUM` / `AD_FREE`), handed through `PremiumPurchaseHelper` into `GooglePlayBilling` and `LoadInventoryTask`, which queries Play for `getAll()` and sorts results with `isMonthly/isYearly/isCurrentMonthly/isCurrentYearly` via `Products.create()`.
## Key pieces
- `all` — every SKU string (current + legacy + alternatives) sent to Play in `querySkuDetailsAsync`.
- `monthlys / yearlys` — the id sets that classify a returned product as monthly vs yearly.
- `isCurrentMonthly / isCurrentYearly` — whether the id is the one currently offered for new purchases (first id in each `Sku`); older ids are recognize-only.
- `getType()` — the Play product type (`SUBS`); package-private so only the billing package uses it.
## Junior notes
- `addAlternativeSkusMonthly/Yearly` return `this` for chaining in `GoogleBillingUtil`; the constructor already registers the primary SKUs, so each id must be added exactly once or `all` gets duplicates.
- Classification is string-equality on SKU ids; a typo in `GoogleBillingUtilStrings` silently drops that product from `Products` instead of erroring.
