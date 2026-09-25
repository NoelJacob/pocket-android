# Pocket/src/main/java/com/pocket/app/list/ContinueReading.java
## What this is
This is the "Continue Reading" nudge: when you return to Pocket, a snackbar-style card offers to reopen the article you were last reading. It is a `Feature` (a gated singleton capability) written in Java, deciding once per app session whether any item qualifies, and logging view/dismiss analytics.
## How it fits
`MyListFragment.setupContinueReading()` calls `checkForContinueReading(listener)` on every `onResume`. That queries the local store for one item: unread, article, 20+ seconds read, at most 95% scrolled, most recently position-updated — and not the same item already shown (tracked in the `CONTINUE_READING_SHOWN_ID` pref). If one qualifies, the fragment builds a `ContinueReadingView` that opens the reader on tap; view/dismiss taps route back here to `trackView`/`trackDismiss`, which log a `pv` (page-view) action in the `CONTINUE_READING` section.
## Key pieces
- `checkForContinueReading(listener)` — the six-rule gate; no-ops when the feature is off or already checked this session (`hasCheckedThisAppSession`); the listener only fires when an item qualifies (no "empty" callback).
- `setupAppPresenceChangedListener()` — resets the once-per-session flag whenever the user returns to the app, so the nudge can appear again next visit.
- `devReview()` — internal-company builds can force-show via the `DEVCONFIG_SNACKBAR_ALWAYS_SHOW_URL_CR` pref, bypassing the shown-ID check.
- `trackEvent(it, dismiss)` — builds the analytics action with timestamp/section/context, adding a DISMISS event only for dismissals.
- `isEnabled` (always true) vs `isOn` (`CONTINUE_READING_ENABLED` pref) — the standard `Feature` kill-switch pair.
## Junior notes
- `@Singleton @Inject` means one instance app-wide; the `hasCheckedThisAppSession` flag is in-memory only and resets on process death — that's fine, it's a rate-limit, not state.
- The query runs on `pocket.sync(...)` against local data with an `onSuccess` callback (no coroutine) — this file predates the Kotlin/Flow repositories; match surrounding Java style if you touch it.

