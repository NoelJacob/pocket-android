# Pocket/src/main/java/com/pocket/sdk/util/view/tooltip/OutsideTouchAction.java
## What this is
A two-field policy object describing what happens when the user taps outside a tooltip: whether the tap is swallowed or passed to views below, and whether the tooltip dismisses. It is passed through `Tooltip.Builder.setOutsideTouchAction()` and enforced by `TooltipViewsHolder`'s touch handling.
## How it fits
Chosen at tooltip build time (default: pass touches through, dismiss on outside tap) and read by `TooltipViewsHolder`'s frame touch listener and click-forwarder. Onboarding coach marks and button hints pick different policies depending on whether the user must interact with the tooltip first.
## Key pieces
- `Block` (`EVERYWHERE`, `IF_NOT_ON_ANCHOR`, `NOWHERE`) — how much touch to swallow. WHY: coach marks block everything, button hints let anchor taps through so the user can still press the highlighted button, passive tips block nothing.
- `block` — the chosen interception level. WHY: separate from dismissal so a tooltip can be sticky yet non-modal, or modal yet persistent.
- `dismiss` — whether an outside tap auto-dismisses. WHY: hints vanish on any tap, while forced flows stay until their button is pressed.
## Junior notes
- `IF_NOT_ON_ANCHOR` still needs the frame click listener to proxy anchor taps; blocking alone does not forward the click, so pair this config with the holder's click path.
- The default (`NOWHERE`, dismiss) is the least surprising; only escalate to `EVERYWHERE` when the user genuinely cannot proceed without acknowledging the tooltip.
