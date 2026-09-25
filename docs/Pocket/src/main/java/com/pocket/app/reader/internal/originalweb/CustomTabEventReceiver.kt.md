# Pocket/src/main/java/com/pocket/app/reader/internal/originalweb/CustomTabEventReceiver.kt
## What this is
Receives button taps from inside the Chrome Custom Tab (the in-app browser view) showing the original web page. The custom tab runs outside the app's view hierarchy, so its menu and previous/next buttons report back here via broadcast intents, and this receiver translates them into app navigation.
## How it fits
Registered as the `PendingIntent` behind the custom tab's Pocket-menu action button and its previous/next secondary toolbar (both built in `OriginalWebFragment`). `ACTION_OPEN_MENU` opens `OriginalWebOverlayActivity` (the reader toolbar as an overlay sheet) for the tab's current URL; `ACTION_PREVIOUS_NEXT_CLICKED` records which arrow was tapped in `OriginalWebFragment.resumeAction` and brings `MainActivity` forward so the fragment's `onResume` can act on it.
## Key pieces
- `onReceive(...)` — the single dispatch on `intent.action`; a null context/intent safely does nothing.
- `ACTION_OPEN_MENU` branch — prefers the URL the tab was launched with (via `UrlUtil.areUrlsTheSame` against both raw and resolved URLs) because the browser may normalize http→https; only a genuinely different URL (user clicked a link) is passed through as-is. Launches the overlay with `FLAG_ACTIVITY_NEW_TASK` since a receiver has no activity context.
- `ACTION_PREVIOUS_NEXT_CLICKED` branch — reads `EXTRA_REMOTEVIEWS_CLICKED_ID` to set `resumeAction` to `PREVIOUS` or `NEXT`, then restarts `MainActivity` to return to the app.
## Junior notes
- `resumeAction` / `urlCustomTabsWasLaunchedWith` are static vars on the fragment — a pragmatic bridge because broadcasts can't easily reach the live fragment instance; they are reset in `handleResumeAction` after use.
- `@AndroidEntryPoint` on a receiver looks unused (nothing is injected yet) but keeps Hilt wiring ready if dependencies are added later.
