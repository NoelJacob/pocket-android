# Pocket/src/main/java/com/pocket/app/reader/internal/originalweb/OriginalWebFragment.kt
## What this is
The "original web view" reader screen: instead of Pocket's parsed article, it opens the publisher's live page in a Chrome Custom Tab (an in-app browser with Pocket-branded toolbar, menu button, and previous/next arrows). The fragment itself is nearly invisible — it launches the tab and handles what happens when the user returns.
## How it fits
A destination in `ReaderFragment`'s nav graph, opened with a URL nav arg. On first `onResume` it calls `launchCustomTab()`; when the user comes back (tab closed, arrow tapped, or "switch to article view" chosen from the overlay menu), `handleResumeAction()` routes via `readerFragment` pager callbacks or a `switchToArticle` navigation. Reader-level `NavigationEvent`s are handled with `enter*` (push) vs `switchTo*` (replace) per `addToBackstack`. `OriginalWebViewModel` is an empty databinding placeholder.
## Key pieces
- `launchCustomTab()` — looks up the item for its resolved URL, honors the user's preferred-browser setting (with a chooser toast), pins the tab to a Custom-Tabs-capable browser so apps like YouTube can't hijack it, and falls back to a plain browser intent (or an error toast + back) when Custom Tabs is unavailable. Sets `hasLaunched` so returning doesn't relaunch.
- `addColors()` / `addIcon()` / `addPreviousAndNext()` — builder extensions theming the tab to the app theme, adding the Pocket-menu action button (broadcast to `CustomTabEventReceiver.ACTION_OPEN_MENU`), and adding the previous/next secondary toolbar when the reader queue has neighbors.
- `handleResumeAction()` — dispatches the static `resumeAction` (`CLOSE` → back, `NEXT`/`PREVIOUS` → pager, `SWITCH_TO_ARTICLE_VIEW` → parsed article) then resets the statics so a stale action can't replay.
- `hasLaunched` persistence — saved/restored across rotation via `OPENED` so rotating inside the tab doesn't launch a second one.
- `handleNavigationEvent(...)` — same enter/switch split as the sibling screens, using this fragment's own directions.
## Junior notes
- The static `resumeAction` / `urlCustomTabsWasLaunchedWith` fields are written by `CustomTabEventReceiver` (a separate component) — always reset after reading, and never rely on them surviving process death.
- Exit animations are set to no-animation with a comment that they don't work — that's a Custom Tabs platform quirk, not dead code to "fix".
