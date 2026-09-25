# pocket-ui/src/main/java/com/pocket/ui/view/button/BoxButton.java

## What this is
The standard filled "box" button of the old View-system UI (the pre-Compose toolkit where screens are built from XML layouts and View classes). For the user it is a rounded teal rectangle with centered white text, used for primary actions like "Save" or "Continue". The class itself is tiny: it just applies the standard text color (`pkt_button_text`) and background fill (`pkt_button_box_fill`) on top of its base class.

## How it fits
Created either from XML layouts or in code — e.g. `AppBar` builds one per action-label button in its overflow/actions row, and `InfoPagingView` uses one as its paging action button. It extends `BoxButtonBase`, which supplies the font, padding, and clickable behavior, and it paints itself with `ButtonBoxDrawable`, which draws the rounded fill and reacts to pressed/disabled states. Do not confuse it with `BoxButton.kt`: same name, but that one is the Jetpack Compose (the newer declarative UI toolkit) equivalent — the two share no code and exist so old and new screens each have a box button.

## Key pieces
- `BoxButton(context[, attrs[, defStyle]])` — three standard View constructors so it can be inflated from XML or built in code; each delegates to `init`.
- `init(context)` — WHY it exists: the entire visual identity of this variant in two lines — state-aware text color plus a `ButtonBoxDrawable` fill. Subclasses like `ErrorButton` and `UpgradeButton` override exactly this to get a different color with identical shape/behavior.

## Junior notes
- `BoxButtonBase` is package-private, so outside the `button` package you reference `BoxButton`, never the base directly.
- Disabled state is handled by dimming the whole view to half alpha (see `PktViewsKt.updateEnabledAlpha`), not by swapping colors — so a greyed-out box button is just this same button at 50% opacity.
