# Pocket/src/main/java/com/pocket/app/settings/premium/PremiumSettingsFragment.java
## What this is
This is the Premium subscription details screen. For users who ever had Premium it fetches live purchase status and shows subscription type, purchase/renewal dates, a manage-subscription link routed by purchase source (Google Play, web, or help article), and per-feature status rows. For never-subscribed users it shows a compact upsell plus FAQ/contact rows. It refreshes whenever the login premium flag flips and survives rotation via saved purchase status.
## How it fits
Extends AbsPrefsFragment; shown from PrefsFragment's Premium row via show() (dialog vs PremiumSettingsActivity by FormFactor). Data comes from two remote syncs: getuser for the all-time status gate, then purchaseStatus for the details. Header visibility (the premium banner from getBannerView) toggles with subscription is_active.
## Key pieces
- `fetchInfo()`: two-step load — WHY getuser first: it flushes pending purchase actions AND tells free users (NEVER status) to render immediately without the second call; shows progress/error-with-retry around both.
- `onStart premiumSubscription`: subscribes to loginInfo premium_status changes and re-fetches purchaseStatus on flip — WHY the screen self-heals after a purchase or expiry elsewhere.
- `createPrefs(prefs)`: branches on premium_alltime_status; active subs get type/date/source rows + renew/manage links, lapsed subs get a renew row opening the paywall via showRenewScreen, features list maps each PremiumFeatureStatus to an FAQ-link row.
- `onSaveInstanceState/onStop`: parcels PurchaseStatus across rotation and stops the subscription to avoid leaking the observer.
## Junior notes
- createPrefs early-returns when status/subscription_info is still null — the fetch callbacks call rebuildPrefs when data lands, so the first paint is legitimately empty; do not "fix" it by blocking.
- Purchase-source routing matters: Google Play subscriptions deep-link into the Play subscriptions page (with order sku when active), web goes to the Pocket manage page, anything else falls back to the help article.
