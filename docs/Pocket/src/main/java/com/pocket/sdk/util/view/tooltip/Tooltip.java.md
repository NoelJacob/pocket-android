# Pocket/src/main/java/com/pocket/sdk/util/view/tooltip/Tooltip.java
## What this is
The overlay hint system: small popup bubbles anchored to a button or a list item that explain or highlight part of the UI. A `Builder` assembles tooltip views plus touch policy and listeners; `show()` anchors to a view (or adapter item) with retries; `TooltipController` tracks the anchor as it moves, handles back-press, and dismisses. `DefaultTheme` offers one-line styled tooltips.
## How it fits
Screens call `Tooltip.DefaultTheme.showButton(view, text, listener)` or build a custom `new Tooltip.Builder(context).addView(new CaretTooltip...).show(anchor, isScrollable)`; `SimpleTheme` builds the standard look. The controller renders through `TooltipViewsHolder` inside a `ViewGroupDisplayer`, follows movement via `ViewBoundsWatcher` (or `AdapterViewWatcher` for list rows), and reports show/fail/dismiss through `TooltipListener`.
## Key pieces
- `Builder` (`addView`, `setOutsideTouchAction`, `setDisplayLocation`, `setTooltipListener`) — assembles layered tooltip views (last added on top) plus policy. WHY: one builder supports single bubbles and composed multi-view hints.
- `show(anchor, isAnchorScrollable)` / `showAdapterItem(item, adapterView)` — anchor to a fixed view or a list item's row, defaulting the display parent to the content root. WHY: fixed views and recycled list rows need different lookup and tracking.
- `TooltipController.anchor()` — tries `applyAnchor()`, watches bounds on success, or retries a couple of run loops when the anchor is not laid out yet before failing. WHY: tooltips are often requested before first layout, and one retry usually succeeds.
- `anchorAdapterItem()` — finds the row, anchors, and installs an `AdapterViewWatcher` to follow recycling. WHY: list rows change view objects under scrolling.
- `dismiss()` / `dismiss(reason)` / `clickAnchor(reason)` — hide with animation; `clickAnchor` proxies a tap into the anchor (including list-item clicks) before dismissing. WHY: "tap the hint to press the button" flows work without the user aiming twice.
- `DefaultTheme` — static shortcut onto a shared `SimpleTheme`. WHY: most call sites want the standard bubble with zero setup.
- `TooltipListener` / `DismissReason` (`DISMISS_REQUESTED`, `ANCHOR_CLICKED`, `BUTTON_CLICKED`) — show/fail/dismiss callbacks with why. WHY: onboarding sequences advance differently per dismissal cause.
## Junior notes
- Showing retries only ~2 run loops; if the anchor is in a not-yet-attached hierarchy (e.g. a not-yet-shown tab), the tooltip fails via `onTooltipFailed` rather than waiting, so trigger from laid-out views.
- The controller registers a back-press listener on the anchor's activity while shown; dismissing removes it, but leaking a controller (never dismissing) leaks that registration.
