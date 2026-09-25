# Pocket/src/main/java/com/pocket/sdk/premium/billing/PremiumProduct.java
## What this is
The store-agnostic base class for something the user can buy: just a price, title, and description. It exists so purchase UI can work with any store (`GooglePlayProduct` today) without knowing Play details.
## How it fits
Subclassed by `GooglePlayProduct`, which fills these fields from Play `SkuDetails`. Consumed by the purchase screen and by `PremiumPurchaseHelper.startPurchase()` / `GooglePlayBilling.startPurchase()`, which cast back to the store type to launch the Play flow.
## Key pieces
- `getPrice / getTitle / getDescription` — display strings sourced from the Play Console listing; price is already localized (e.g. "$4.99").
- `toString()` — title, price, description joined for logging.
## Junior notes
- Abstract with no abstract methods: it is a plain data holder kept open so a future store (e.g. web payments via `showWebPaymentFlow`) can subclass it.
- Never construct this directly in UI code; always use the instances from `Products.getMonthly()/getYearly()`.
