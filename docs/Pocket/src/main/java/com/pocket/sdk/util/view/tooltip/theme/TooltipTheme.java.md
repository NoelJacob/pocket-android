# Pocket/src/main/java/com/pocket/sdk/util/view/tooltip/theme/TooltipTheme.java
## What this is
The interface for an app-wide tooltip style: three convenience methods for showing a text hint on a button (default or explicit parent) or on a list item. It lets the whole app's tooltip look be swapped by replacing one implementation. The live implementation is `SimpleTheme`.
## How it fits
`Tooltip.DefaultTheme` holds a static `SimpleTheme` behind this interface; screens call `showButton` / `showAdapterItem` and get back a `TooltipController` without touching builders. Introducing a new visual style means writing a new `TooltipTheme` and pointing `DefaultTheme` at it.
## Key pieces
- `showButton(button, text, listener)` / `showButton(button, displayLocation, text, listener)` — hint on a fixed view, optionally in an explicit parent. WHY: most hints point at toolbar or dialog buttons.
- `showAdapterItem(item, adapterView, text, listener)` — hint on a list row's view. WHY: list-anchored hints need the adapter lookup path, not a raw view.
## Junior notes
- The `text` parameter is a string resource id, not a raw string; for dynamic text drop down to `Tooltip.Builder` with a `CaretTooltip` built from a `CharSequence`.
- All methods return the controller; keep it if the hint must be dismissed programmatically (e.g. the user navigates away), since themed shortcuts register no auto-cleanup.
