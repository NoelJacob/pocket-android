# Pocket/src/main/java/com/pocket/app/ReviewPrompt.kt
## What this is
The policy object deciding when to ask the user for a Play Store review. It shows at most once per three months, only right after the user finishes (90 percent-plus of) an article in the Reader, and only once they have used the Reader at least 4 times (via `FeatureStats`). Every show/submit/dismiss/error outcome is tracked as analytics, and internal builds can force-show it via a debug pref.
## How it fits
Reader exit calls `onExitReader(item)` (which arms the "just finished" flag on high progress); the screens the user returns to check `shouldShow()` on resume and launch the Google review flow when true, then report `onShow`/`onReview`/`onDismiss`/`onReviewPromptError`. `onResumeAnotherScreen` disarms the flag on any other resume so the prompt only ever fires immediately after Reader. `App` exposes it as `reviewPrompt()`.
## Key pieces
- `shouldShow`: the three-gate check (finished-article flag AND cooldown elapsed AND reader-use count) plus the internal force path that consumes itself; WHY the force pref resets on read is so a stray debug toggle cannot spam real prompts.
- `onShow` / `onReview` / `onReviewPromptError`: snooze-3-months, never-ask-again (`Long.MAX_VALUE`), and snooze-on-error respectively; WHY error also snoozes is the Google API reports failures opaquely, so retrying immediately would likely fail again.
- `onExitReader` / `onResumeAnotherScreen`: arm/disarm pair; progress reads the article position, falling back from ARTICLE to WEB percent.
- `Analytics` interface + secondary constructor: wraps a page-view action on the review-prompt view with submit/cancel events synced via `pocket.sync`; WHY an interface with a default impl is testability: tests inject a fake instead of the sync engine.
## Junior notes
- The Google review API never tells us if the sheet showed or the user reviewed, so `onReview` assumes success and permanently silences; call it only after actually requesting the flow.
- `Clock` is injected (not the system clock) so tests can time-travel the 3-month cooldown; always use the injected clock for new time logic here.
