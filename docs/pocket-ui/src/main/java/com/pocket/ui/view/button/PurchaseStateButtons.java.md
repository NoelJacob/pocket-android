# pocket-ui/src/main/java/com/pocket/ui/view/button/PurchaseStateButtons.java

## What this is
The Premium purchase-screen button block: for the user either a spinner (prices loading), a single fallback button (prices failed to load), or two side-by-side plan buttons (monthly/annual) with an optional circular "Save N%" badge overlapping the second button's top-right corner. It is a `ConstraintLayout` (a layout positioning children by rules relative to each other) inflating `view_purchase_state_button.xml`, driven by a three-value `State`.

## How it fits
Lives on the Premium purchase screen: the screen's ViewModel (the class holding screen data and logic) fetches price data, binds each plan into `option1()` / `option2()` via the `PurchaseButton.Binder`, sets the badge text with `setBadge(...)`, and flips `setState(...)` between LOADING → SHOW_PRICES (or SHOW_UNKNOWN_PRICE on error). `init` pre-styles option 1 as the outlined look (themed-grey text on `pkt_bg` fill with grey outline) and option 2 as the coral-filled default via `bind().clear()`, and gives the badge its circular `SaveBadgeDrawable` background.

## Key pieces
- `State { LOADING, SHOW_UNKNOWN_PRICE, SHOW_PRICES }` + `setState(state)` — WHY it exists: the whole visibility choreography in one switch — LOADING shows only the spinner, SHOW_UNKNOWN_PRICE shows only the fallback button, SHOW_PRICES reveals each option only if its `PurchaseButton.isSet()` (both lines bound) and then re-evaluates the badge.
- `optionUnknown() / option1() / option2()` — accessors exposing the three buttons for binding click listeners and text; the container deliberately does no price logic itself.
- `setBadge(badgeText)` → `updateBadgeVisibility()` — shows the badge only when it has text *and* both options are visible; otherwise INVISIBLE (keeps layout space, unlike GONE).
- `fixBadgeMargins(badgeVisible)` — shifts option 2's margins to make room for the overlapping badge and adjusts the container's own right margin; WHY manual margin surgery: the badge hangs outside the button bounds, so the layout needs compensating insets or the badge clips at the screen edge.
- `SaveBadgeDrawable` (private inner class) — draws the badge's filled circle with its own state-aware colors.

## Junior notes
- INVISIBLE vs GONE matters here: options hide with INVISIBLE during LOADING (preserving measured space so the spinner doesn't jump the layout) but the badge logic and SHOW_PRICES use a mix — check which constant a branch uses before "simplifying".
- The Figma link in the class comment is the visual spec for this block; if the badge position or button styling looks off, compare against that design first.
