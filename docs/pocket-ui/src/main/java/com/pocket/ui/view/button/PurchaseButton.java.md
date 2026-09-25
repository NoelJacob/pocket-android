# pocket-ui/src/main/java/com/pocket/ui/view/button/PurchaseButton.java

## What this is
A two-line plan/price button for the Premium purchase screen. For the user it looks like a `BoxButton` but with two centered lines: the plan name on top (e.g. "Annual") and the price or term below (e.g. "$44.99/yr"). It is a `VisualMarginConstraintLayout` (a layout that aligns *visible* glyph edges rather than view bounds, so text looks optically aligned) inflating `view_purchase_button.xml`, which holds the `text` and `subtext` TextViews.

## How it fits
Never used alone — `PurchaseStateButtons` owns two of them (`option1`, `option2`, e.g. monthly vs annual) plus a loading/error state, and configures each through the fluent `Binder` (a chained-setter helper: `bind().text(...).subtext(...).onClick(...)`). The binder's `clear()` resets to the coral-fill default; `PurchaseStateButtons.init` then restyles option 1 as the outlined "unselected" look. There are no databinding adapters (XML-to-field binding helpers) — parents drive it imperatively from code. Disabled state dims via `PktViewsKt.updateEnabledAlpha`, same as all box buttons.

## Key pieces
- `init(attrs)` — inflates `view_purchase_button`, grabs `text`/`subtext`, and tunes padding so the two lines read as one block (top line keeps top padding only, bottom line keeps bottom padding only); note `attrs` is currently unused — no custom XML attributes.
- `isSet()` — true only when *both* lines have text; WHY it exists: `PurchaseStateButtons.setState(SHOW_PRICES)` uses it to hide options whose price data never loaded.
- `bind()` → `Binder` — the only configuration surface: `clear()` (reset art/colors), `text(resId)` (0 clears), `subtext(s)`, `background(drawable)`, `textColor(colors)` (sets both lines), `onClick(listener)`. WHY a binder instead of plain setters: plan buttons are configured in one chained statement at purchase-screen setup, and `clear()` gives a known baseline before restyling.
- `setEnabled(enabled)` — dims the whole two-line button at 50% alpha when disabled.

## Junior notes
- `Binder` is an inner (non-static) class holding an implicit reference to its button — don't store a Binder beyond setup; keep the `PurchaseButton` and call `bind()` fresh if you need to reconfigure later.
- `text(@StringRes int)` takes a string *resource id*, while `subtext(CharSequence)` takes a literal/price string — mixing them up (passing a price string id to subtext is fine, passing a raw string to text won't compile) is the common mistake.
