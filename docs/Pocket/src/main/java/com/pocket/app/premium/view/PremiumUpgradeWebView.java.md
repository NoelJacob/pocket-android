# Pocket/src/main/java/com/pocket/app/premium/view/PremiumUpgradeWebView.java
## What this is
This is a `WebView` (an embedded browser widget) that loads the web checkout page (`getpocket.com/android/purchase`) when Google Play billing is unavailable. It watches every URL the page navigates to, and when it detects a return to a Pocket web URL it re-syncs the account to confirm the user actually gained Premium. It is the fallback cash register for the old paywall.
## How it fits
`PremiumPurchaseFragment.setWebPaymentVisible(true)` calls `inflate(listener)`, which configures and loads the checkout URL; loading and completion events go back through `WebViewPurchaseCallbacks` so the fragment can show its spinner and then fire `onPurchaseComplete`. `listenForPurchaseConfirmation` does the verification by calling `pocket.syncRemote` and checking `premium_status`.
## Key pieces
- `inflate(listener)` — one-time WebView setup (JavaScript on, zoom off, focus hack for the soft keyboard) plus `loadUrl(URL)`; the `isInflated` guard means repeat calls just reload rather than re-adding clients.
- `WebClient` — a `WebViewClient` that calls `listenForPurchaseConfirmation` from three hooks (`onPageFinished`, `onLoadResource`, `shouldOverrideUrlLoading`) because some Android versions miss redirects in any single one.
- `onPurchaseConfirmed` — flips `isPurchaseConfirmed` once and notifies the listener; the flag prevents double-firing as further resources load.
## Junior notes
- The focus hack plus touch listener exists because of an old Android bug where the keyboard would not appear for inputs inside dialog-hosted WebViews — do not "simplify" it away.
- `DeepLinks.Parser.isPocketWebUrl` is the heuristic for "checkout finished and bounced back to Pocket"; any change to the web checkout redirect URLs must be mirrored here.
