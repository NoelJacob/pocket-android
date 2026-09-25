# Pocket/src/main/java/com/pocket/app/premium/PremiumAnalytics.java

## What this is
NOTE: this source file no longer exists in the repo (removed after the chunk list was generated). Per the last generated summary, it implemented the Premium purchase page analytics contract (`PremiumPurchase.Analytics`): carousel views, purchase clicks, successes, failures, and page views.

## How it fits
Previously wired into the `PremiumPurchase` screens to log purchase-funnel events. For current analytics behavior, look at the purchase screens under `settings/premium` and the shared analytics context helpers.

## Key pieces
- Former `trackCarouselView` / `trackPurchaseClick` / `trackPurchaseFailure` / `trackPurchaseSuccess` / `trackView` — WHY: one method per funnel step so the purchase screen stayed free of tracking code.

## Junior notes
- Do not resurrect this file; re-implement funnel events at the current purchase screen if they are missing.
