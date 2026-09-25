# pocket-ui/src/main/java/com/pocket/ui/view/button/UpgradeButton.java

## What this is
The Premium-upsell variant of the box button. For the user it looks like the standard `BoxButton` — same shape, font, padding, and behavior — but with white text on the upgrade fill (`pkt_button_upgrade_fill`, the Premium/coral color), used wherever the app nudges free users toward Premium ("Upgrade", "Go Premium").

## How it fits
A sibling of `BoxButton` and `ErrorButton`: all extend `BoxButtonBase` and differ only in their `init` recolor. Place it anywhere a `BoxButton` would go when the action is specifically an upsell; enabled-dimming, font, and `ButtonBoxDrawable` state handling are inherited unchanged. Note it uses `getColor(...)` (a single flat color) for its text rather than a state list — the label stays white in all states while only the background reacts.

## Key pieces
- `UpgradeButton(context[, attrs[, defStyle]])` — standard View constructors for XML or code use.
- `init(context)` — WHY it exists: the entire variant — white text plus a `ButtonBoxDrawable` filled with the upgrade color. Compare with `BoxButton.init`/`ErrorButton.init` to see the one-line-per-variant pattern.

## Junior notes
- If the design adds more colored variants, follow this file's shape (new subclass + new fill color) rather than adding a color flag to `BoxButton` — that keeps every call site's intent visible in the layout.
- Don't confuse with the purchase-screen `PurchaseButton` (two-line plan/price card): `UpgradeButton` is the single-line upsell nudge; `PurchaseButton` is the plan picker.
