# pocket-ui/src/main/java/com/pocket/ui/view/edittext/PktEditText.java

## What this is
The standard Pocket text box: a rounded thin-grey-oval field with comfortable fixed padding, top-aligned multi-line text, and a theme-aware hint color. The user sees the familiar soft pill-shaped input used for free-form text entry, with a background that shifts when focused. It has no label, counter, or buttons; it is purely the styled input primitive.

## How it fits
Used wherever a plain themed input is needed without the labeled/floating or char-count machinery (search inputs, simple form fields, note editors). It extends ThemedEditText (Pocket's theme-aware EditText that applies theme colors and typefaces), and everything happens in `init()`: text appearance `Pkt_Text_EditText`, horizontal padding from `pkt_space_md`, 14dp vertical padding, hint color `pkt_themed_grey_3`, a ButtonBoxDrawable background (a rounded-rect drawable that reacts to focus), and `Gravity.TOP` so multi-line text starts at the top. LabeledEditText and CharCountEditText are separate composite widgets, not subclasses of this.

## Key pieces
- `init()`: the entire behavior; sets text appearance, padding, hint color, background, and gravity in one place so every PktEditText looks identical without XML styling.
- `ButtonBoxDrawable(pkt_bg, pkt_focusable_grey_4)`: the rounded background that changes between default and focused colors; WHY a custom drawable is the oval shape plus focus ring stock backgrounds cannot express.
- `setGravity(TOP)`: anchors text to the top for multi-line fields instead of vertical centering.

## Junior notes
- ThemedEditText (parent) handles day/night theme colors on top of the stock EditText; this class only adds the Pocket padding/background/gravity recipe.
- No custom XML attributes and no Binder: configure it like a normal EditText (`inputType`, `hint`, `lines`) from layout XML or code.
- For a floating label use LabeledEditText, for a character limit use CharCountEditText; do not hand-roll those on top of this.
