# Pocket/src/main/java/com/pocket/app/premium/PremiumPurchase.java
## What this is
This file defines the contract for the old Premium paywall screen: three small interfaces (`View`, `Presenter`, `Analytics`) that split screen rendering, purchase logic, and event tracking. It contains no logic itself — it is the agreement that `PremiumPurchaseFragment` (the screen) and `PurchasePresenter` (the logic) code against. Think of it as the MVP (Model-View-Presenter, an older alternative to MVVM where a Presenter object drives a passive View) boundary for buying Premium.
## How it fits
`PremiumPurchaseFragment` implements `PremiumPurchase.View` and creates a `PurchasePresenter` plus a `PremiumAnalytics` (the `Analytics` implementation) in `onActivityCreated`. The presenter calls back into `View` methods (`onProductsLoaded`, `onPurchaseComplete`, `setWebPaymentVisible`) as Google Play billing progresses, and the fragment forwards button taps (`option1Click`/`option2Click`) and lifecycle (`bindView`/`unbind`) the other way.
## Key pieces
- `View` — what the fragment must render: loading state, monthly/yearly `Products`, purchase completion, a web-payment fallback view, and the Amazon-help alert. Exists so the presenter never touches Android views directly.
- `Presenter` — what the fragment drives: `bindView` first (view + analytics + any pending purchase), then `bindPurchaseHelper` second. The two-step bind order matters because constructing the billing helper immediately fires a product request, and the view must be ready to receive it without a race.
- `Analytics` — tracking calls (`trackView`, `trackPurchaseClick/Success/Failure`, `trackClose`) the presenter fires at each purchase step. Exists so purchase-funnel logging lives in one injectable seam instead of inside billing code.
## Junior notes
- MVP here means the fragment is deliberately "dumb": if you add a button, add a method to `View`/`Presenter` rather than putting billing calls in the fragment.
- `GooglePlayProduct pendingPurchase` is passed through bind so a purchase that survives rotation can still be attributed to analytics when it completes.
