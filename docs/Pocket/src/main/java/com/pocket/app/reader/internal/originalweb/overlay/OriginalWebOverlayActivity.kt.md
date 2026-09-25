# Pocket/src/main/java/com/pocket/app/reader/internal/originalweb/overlay/OriginalWebOverlayActivity.kt
## What this is
A transparent full-screen host that displays the reader menu (save, share, tags, listen, switch-to-article) on top of the Chrome Custom Tab showing the original web page. Tapping outside the sheet dismisses it; closing the listen mini-player also finishes it.
## How it fits
Started by `CustomTabEventReceiver` with the tab's current URL in `URL_EXTRA`; it immediately shows `OriginalWebBottomSheetFragment` with that URL, or finishes if no URL was supplied. Hilt DI (constructor params provided automatically) supplies `Theme` and the `Listen` text-to-speech controller. Uses a transparent dialog theme (`themeOverride`) with no background so the browser stays visible behind the sheet.
## Key pieces
- `onCreate(...)` — forces full-screen layout params with dialog animations, sets a root touch listener that finishes on outside-tap, shows the bottom sheet (or finishes when the extra is missing), and subscribes to listen state.
- `setupListen()` / `setupListenCloseButtonClickListener()` — when the listen mini-player is showing, hijacks its close button so tapping it stops playback (`trackedControls(null, null).off()`) and finishes the overlay; the `Handler.post` covers the case where the player view isn't laid out yet, and the subscription covers it appearing later.
- `onDestroy()` — disposes the listen subscription to avoid leaking the activity.
- `URL_EXTRA` — the intent key carrying the page URL to the sheet.
## Junior notes
- `getAccessType() = ANY` means no login is required to open this — the sheet itself gates signed-in actions (e.g. tagging) with a sign-in detour.
- `findViewById(R.id.listen_mini_close)` reaches into the player view owned elsewhere — if that id is renamed, this silently stops wiring (null-safe `?.`), so the overlay would no longer close with the player.
