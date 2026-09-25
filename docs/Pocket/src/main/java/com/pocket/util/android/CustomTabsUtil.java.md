# Pocket/src/main/java/com/pocket/util/android/CustomTabsUtil.java

## What this is
A small wrapper for opening web links in a Chrome Custom Tab (an in-app browser tab that shares the user's browser state but is themed like Pocket). It solves "open this URL nicely or explain why we can't": it tints the tab toolbar with the current theme color and, when no browser exists to handle the link, shows a "browser not found" dialog instead of crashing. It is currently shelved — marked to be kept around so Custom Tabs can be re-added easily.

## How it fits
It is a static-only utility (`private` constructor) with no current callers in `Pocket/src` — link opening today flows through `IntentUtils.openWithDefaultBrowser` (used by `AddActivity`, `PocketUrlHandlerActivity`). It borrows `IntentUtils.isActivityIntentAvailable` for its availability check and reads the theme from `App`/`Theme` plus `pkt_themed_teal_2` / `pkt_bg` colors. Downstream it launches the Custom Tab intent or the fallback dialog.

## Key pieces
- `viewUrl(context, url)`: builds a themed `CustomTabsIntent` and launches it when some activity can handle it; otherwise shows the not-found dialog. Returns whether the URL was opened. WHY it exists: single choke point for themed in-app browsing.
- `getToolbarColor(context)`: resolves the toolbar color against the current light/dark theme state. WHY it exists: the tab chrome must match Pocket's theme or it looks broken.
- `warmUp(context)`: connects to the Custom Tabs service early so later opens feel instant. WHY it exists: hides browser-startup latency behind app-idle time.

## Junior notes
- `@SuppressWarnings("unused")` plus the "keep it around" comment is deliberate: do not delete this as dead code without checking with the team.
- Custom Tabs need a browser that supports the protocol (`getPackageName` may return null) — hence the null guard in `warmUp`.
- Theme colors come from `ContextCompat.getColorStateList` resolved against `Theme.getState(...)`: themed colors are state-dependent, not plain constants.
