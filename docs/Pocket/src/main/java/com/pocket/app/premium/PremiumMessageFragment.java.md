# Pocket/src/main/java/com/pocket/app/premium/PremiumMessageFragment.java

## What this is
The content of the Premium message screen: a header image, title, body, action button, and optional disclaimer for purchase-complete and gifted-Premium notices.

## How it fits
Installed by `PremiumMessageActivity` with the message strings as fragment arguments. The app bar's close icon and the action button both exit via `startDefaultActivity()` (return to the user's default landing screen and finish). Analytics view and screen identifiers split on gift vs upgrade so the two funnels measure separately.

## Key pieces
- `newInstance(isGift, title, message, buttonText, disclaimer, startScreen)` — WHY: bundles all display strings; the fragment never reads the Activity intent directly.
- `onActivityCreated` — WHY: binds views, wires close/button to exit, then shows the intent message — or exits immediately when no title was supplied (gift flows with nothing to say).
- `showIntentMessage()` — WHY: paints the four text slots; the disclaimer hides itself when absent via `setTextOrHide`.
- `getActionViewName` / `getScreenIdentifier` — WHY: gift (`GIFTED` / `PREMIUM_GIFT_MESSAGE`) vs upgrade (`UPGRADE` / `JOINED_PREMIUM`) attribution.
- `startDefaultActivity()` + `finish()` — WHY: this screen is a dead end; it always leaves the back stack rather than navigating somewhere specific.

## Junior notes
- `onActivityCreated` is a legacy lifecycle callback (deprecated in modern fragments) — this old screen still uses it; new screens should use `onViewCreated`.
- `findViewById` here is the fragment's own helper from `AbsPocketFragment`, not the Activity's — views come from `activity_premium_message`, already inflated in `onCreateViewImpl`.
