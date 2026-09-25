# pocket-ui/src/main/java/com/pocket/ui/view/button/CheckBox.java

## What this is
A small toggle icon, not a text box with a tick — for the user it is the checkmark badge used in lists and rows (checked = coral check icon visible in its "on" color, unchecked = dimmed). It extends `IconButton` (a checkable image view with Pocket tinting and tooltip behavior) and hard-codes the check drawable (`ic_pkt_check`), centered scale type, checkable on, and the `pkt_checkbox` tint.

## How it fits
Used wherever a row needs a compact on/off indicator rather than a full switch widget — parent views flip it via the `Checkable` (an interface adding a checked on/off state to a View) methods inherited from `IconButton`/`CheckableImageView`. All sizing, tint-state handling, and long-press tooltip logic come from `IconButton`; this class only fixes the icon art and enables checking.

## Key pieces
- `CheckBox(context[, attrs[, defStyle]])` — standard View constructors; each calls `init()`, so XML and code construction look identical.
- `init()` — WHY it exists: the whole specialization in four lines — check art, `CENTER` scale (draw art at natural size, don't stretch), `setCheckable(true)` (opt into checked-state drawables), `pkt_checkbox` drawable color. No custom XML attributes of its own.

## Junior notes
- It inherits `IconButton`'s `checkedDrawableColor` XML attribute, so a layout can still override the checked tint per-instance without touching this file.
- Don't confuse with Android's framework `CheckBox` (a text + box widget) — this is Pocket's icon-only toggle; imports of `android.widget.CheckBox` and this class cannot mix in one file without an alias.
