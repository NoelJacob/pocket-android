# Pocket/src/main/java/com/pocket/app/premium/PremiumPurchaseFragment.java
## What this is
This is the visible Premium paywall UI: a swipeable feature carousel (permanent library, ad-free, search, tags, highlights, custom fonts) plus monthly/yearly purchase buttons and a web-payment fallback. It implements `PremiumPurchase.View`, owns a `PurchasePresenter` for billing, and shows progress, error, and success states. This is the screen users actually tap "Upgrade" on.
## How it fits
`PremiumPurchaseActivity` creates it via `newInstance(startSource, isRenew)`; on `onActivityCreated` it builds the `InfoPagingView` carousel, wires `PurchaseStateButtons` taps to `presenter.option1Click/option2Click`, and binds a `PremiumPurchaseHelper` (Google Play billing) through the presenter. On success `onPurchaseComplete` launches `PremiumMessageActivity` with a renew-aware message and finishes; when Play billing is unavailable it inflates `PremiumUpgradeWebView` for web checkout instead.
## Key pieces
- `presenter` (`PurchasePresenter`) — holds products, pending purchase, and analytics; the fragment only renders what the presenter reports. Keeps Store billing code out of view code.
- `onProductsLoaded` — fills the two purchase buttons with real Play prices (`lb_prem_purchase_per_month`) once billing returns; `onProductsLoadFailed` falls back to an unknown-price state so the button still retries.
- `setWebPaymentVisible` — inflates the `PremiumUpgradeWebView` overlay and forwards its completion to `onPurchaseComplete`. Exists for devices without Play (or Play errors) so users can still pay.
- `onSaveInstanceState` + `STATE_PURCHASING`/`STATE_PENDING_PURCHASE` — preserves the in-flight purchase across rotation, including delegating to `purchaseHelper().onSaveInstanceState`.
## Junior notes
- Two-step bind order is load-bearing: `bindView` first, then `bindPurchaseHelper`, because constructing the helper immediately requests products and would otherwise race the view setup.
- `getContext()` can be null if the user left mid-purchase, so `onPurchaseComplete` falls back to `app().activities().getVisible()` before giving up on showing the success message.
