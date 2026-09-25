# Pocket/src/main/java/com/pocket/sdk/util/view/tooltip/theme/SimpleTheme.java
## What this is
The app's standard tooltip style: a single-color rounded box with a caret (little arrow) pointing at the target, built from `CaretTooltip`. It implements `TooltipTheme` with the caret pulled 5dp into the anchor for a snug fit. This is what every `Tooltip.DefaultTheme` call renders.
## How it fits
Held as the static theme inside `Tooltip.DefaultTheme`; button and list-item hint requests from screens land here. Each method builds a `Tooltip.Builder` with one `CaretTooltip` view and shows it, returning the controller for dismissal or callbacks.
## Key pieces
- `showButton(button, ...)` — builds a `CaretTooltip` with the given text and slightly negative anchor distance, shown on the button (default content root or explicit parent). WHY: the overlap makes the caret tip touch the button instead of floating.
- `showAdapterItem(item, adapterView, ...)` — same bubble shown on a list row via the adapter lookup path. WHY: shares the exact style between fixed and scrolling anchors.
- `ANCHOR_DISTANCE = -5f` dp — the caret-to-anchor gap. WHY: negative pulls the bubble onto the anchor edge so the pointer reads as attached.
## Junior notes
- The button-text overload of `CaretTooltip.Builder.setText` is always called with button 0 (no button); taps on the bubble proxy-click the anchor, so do not use this theme where the bubble itself needs its own button.
- To change the app-wide hint look, edit this class (colors, corner radius via `CaretTooltip`) rather than call sites; that is the whole point of the theme seam.
