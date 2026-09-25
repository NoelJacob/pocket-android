# Pocket/src/main/java/com/pocket/app/premium/PurchasePresenter.java
## What this is
This is the purchase-logic half of the old paywall: it talks to Google Play billing via `PremiumPurchaseHelper`, tells the `PremiumPurchase.View` what to show, and fires `PremiumPurchase.Analytics` events. It also implements `PremiumPurchaseHelper.PurchaseListener`, so Play billing callbacks (products loaded, purchase state changed, activation failed) land here first and get translated into view updates. The fragment stays free of Store API details.
## How it fits
`PremiumPurchaseFragment` constructs it with `isAmazonBuild`, calls `bindView` then `bindPurchaseHelper`, and forwards button taps to `option1Click`/`option2Click` and retry to `getProducts`. Billing results arrive as listener callbacks (`onProductsLoaded`, `onPremiumPurchased`, `onGooglePlayUnavailable`), which the presenter forwards to the view and analytics; `unbind` tears everything down in `onDestroy`.
## Key pieces
- `startPurchase(product)` — remembers the product as `pendingPurchase` before launching billing, so later success/failure analytics can name what the user actually bought.
- `showWebPaymentFlow` — Play-unavailable fallback: Amazon builds get a help alert (no web checkout), everyone else gets the web view. Encodes a store-policy difference in one branch.
- `onPurchasingStateChanged` — forwards the spinner state to the view, and fires `trackPurchaseSuccess` when billing reaches `ACTIVATING` (server confirming entitlement), which is the analytics "money" moment.
## Junior notes
- Every `view`/`analytics` call is null-checked because `unbind` nulls them and billing callbacks can arrive after the fragment is gone — never remove those guards.
- `isAmazonBuild` changes user-visible behavior (alert vs web flow), so test the paywall on both build flavors if you touch this file.
