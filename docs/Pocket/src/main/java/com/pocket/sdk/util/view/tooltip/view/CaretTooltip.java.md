# Pocket/src/main/java/com/pocket/sdk/util/view/tooltip/view/CaretTooltip.java
## What this is
The standard tooltip bubble view: a rounded box with a caret arrow that points at its anchor, auto-placing itself below, above, right, or left of the target depending on where it fits. It inflates either a plain text layout or one with a button, animates in with a delayed overshoot pop, and draws its own background (rounded rect, caret, drop shadow) via an inner drawable.
## How it fits
Built by `SimpleTheme` (and custom `Tooltip.Builder` call sites) through `CaretTooltip.Builder`, then managed by `TooltipViewsHolder`, which calls `applyAnchor()` to position it and `animateIn()`/`animateOut()` to show and hide it. Taps on the bubble dismiss (or proxy-click the anchor when there is no button). The inner `CaretTooltipDrawable` handles all background painting including dark-mode colors.
## Key pieces
- `Builder.setText(message, button)` — inflates the simple or button layout and wires taps: with a button, taps dismiss; without, taps proxy-click the anchor via the controller. WHY: button-less hints act as an extension of the target itself.
- `Builder.setView(...)` / `setDistance(...)` / `setOnClickListener(...)` — custom content, caret-to-anchor gap, and click handling that dismisses first. WHY: escape hatches for non-text hints without subclassing.
- `applyAnchor(xy, anchorBounds, windowBounds)` — measures content, checks free space on each side in priority order (below, above, right, left), verifies the caret can legally sit on that edge, and writes the chosen x/y into `xy`. Returns false when nothing fits. WHY: the single placement authority; failure here is what makes `Tooltip` retry or give up.
- `fit(tooltipLength, windowMin, windowMax, anchorCenter, acceptableShift)` — centers on the anchor when possible, else slides minimally back on screen, allowing half a shadow width offscreen. WHY: near-edge anchors still get a bubble instead of instant failure.
- `animateIn()` / `animateOut(callback)` — delayed scale-from-92-percent plus fade in; fast fade-and-shrink out that fires the callback for holder bookkeeping. WHY: enter has presence, exit gets out of the way for chained hints.
- `CaretTooltipDrawable` — draws rounded rect, caret on any of four sides, and blurred shadow, re-padding the view as the caret side changes. WHY: caret side is only known at placement time, so background and padding must follow it.
## Junior notes
- `applyAnchor()` must run after the anchor is attached and measured; zero-size or detached anchors return false, which surfaces as `onTooltipFailed` after retries rather than a crash.
- The view is clickable to swallow touches; placing it where it covers the anchor blocks anchor taps except through the proxy path, so keep bubbles offset via the distance setting.
