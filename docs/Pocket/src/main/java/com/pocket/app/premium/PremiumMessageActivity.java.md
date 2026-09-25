# Pocket/src/main/java/com/pocket/app/premium/PremiumMessageActivity.java

## What this is
A login-gated host Activity for one-off Premium messages: the "purchase complete" confirmation and the gifted-Premium (Samsung Galaxy Gifts) notice. It carries the message strings as intent extras and installs `PremiumMessageFragment` to render them.

## How it fits
Launched via `Premium.showPurchaseComplete(...)` or `startGiftMessageActivity(...)` (never by raw intent — the static starters pack the extras). On first creation it builds the fragment from those extras; on rotation the system restores it. Analytics view name delegates to the fragment (gift vs upgrade), and the Listen mini-player is suppressed while this screen is up.

## Key pieces
- `startActivity(context, title, message, buttonText, disclaimer[, startScreen])` — WHY: packs a custom message into extras; the `startScreen` overload optionally records where to return to.
- `startGiftMessageActivity(context)` — WHY: gift-notice entry with no custom strings; note the read default treats a missing boolean extra as a gift message.
- `onCreate` — WHY: creates the fragment from intent extras on fresh launch, re-attaches to the restored fragment otherwise (never creates two).
- `getActionViewName` delegating to `frag` — WHY: gift and upgrade funnels must attribute separately, with an `UPGRADE` fallback before the fragment exists.
- `getDefaultThemeFlag() = FLAG_ONLY_LIGHT` — WHY: this marketing-style screen is always light-themed regardless of app theme.

## Junior notes
- `checkClipboardForUrl` is disabled here so a copied link does not pop an "open URL?" dialog over the confirmation — do not re-enable it.
- Like `ListenSettingsActivity`, this is Activity-shell plus Fragment-content; all rendering lives in `PremiumMessageFragment`.
