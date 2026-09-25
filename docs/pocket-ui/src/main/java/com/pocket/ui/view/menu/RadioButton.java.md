# pocket-ui/src/main/java/com/pocket/ui/view/menu/RadioButton.java

## What this is
Pocket's round radio-dot indicator: an empty circle when off, a filled teal dot when checked. It is just a pre-styled `IconButton` — no text, no custom drawing — centered in its bounds.

## How it fits
Used as the selection indicator inside radio-style rows (e.g. alongside `RadioOptionRowView` content) wherever a single choice among many is shown. Hosts treat it like any checkable `IconButton`: set its checked state and the drawable reflects it. The teal color comes from `R.color.pkt_themed_teal_2`, so it follows light/dark theme automatically.

## Key pieces
- `RadioButton` — extends `IconButton`; the three constructors all funnel into `init()`.
- `init()` — centers the drawable (`ScaleType.CENTER`), applies the themed teal tint, and sets the `btn_radio_mtrl` drawable (a state-list whose checked state shows the filled dot).

## Junior notes
- All styling here is "configure the base class," not custom `onDraw` — if the dot looks wrong, check `IconButton`'s tint/checked handling and the `btn_radio_mtrl` state-list drawable first.
- The `TODO` in the source is real: design had not finalized this style, so expect it to change if the radio visuals get updated.
