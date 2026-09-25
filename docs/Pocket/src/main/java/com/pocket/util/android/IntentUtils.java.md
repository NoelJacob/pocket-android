# Pocket/src/main/java/com/pocket/util/android/IntentUtils.java

## What this is
The app's "is it safe to fire this intent, and which app should take it?" toolbox for Android intents (message objects that ask the system or another app to do something, like view a URL). It solves the crash where `startActivity` throws when no app can handle the intent, plus the UX problems of picking browsers and translating text. For example, `PocketUrlHandlerActivity` opens resolved URLs via `openWithDefaultBrowser`, and the Reader's text-selection menu translates via `googleTranslate`.

## How it fits
It is a static (`abstract`, non-instantiable) helper used across feature code: `App`, `AddActivity`, and `PocketUrlHandlerActivity` for safe launches and browser opening; `Email.startEmailIntent` and `CustomTabsUtil.viewUrl` for availability checks; `ArticleActionModeCallback` for Google Translate from selected Reader text. It leans on `IntentUtils2.isIntentUsable` for the Translate check. Downstream it either launches the resolved app or shows a fallback (toast/dialog), never crashing.

## Key pieces
- `isActivityIntentAvailable(context, intent)`: true when something can handle the intent. WHY it exists: the guard every `startActivity` call site needs.
- `safeStartActivity(context, intent, clearWhenTaskReset)`: launches only when safe, returning success. WHY it exists: one guarded spelling so callers stop hand-rolling try/catch.
- `getMatchingApps(intent, context)` / `getAllAvailableBrowsers(context)`: list candidate apps (preferred first) and the browser subset. WHY they exist: powering choosers and "open in browser" flows.
- `isAppInstalled(context, packageName)` / `getDefaultBrowser()`: package presence and the system's default browser component. WHY: feature gating and explicit-browser opens.
- `openWithDefaultBrowser(context, viewIntent, allowResolverActivity)`: opens a URL in the default (or a common) browser without bouncing back into Pocket itself. WHY the care: a naive view intent can re-resolve to Pocket and loop forever (`AddActivity` and `PocketUrlHandlerActivity` both rely on this).
- `getGoogleTranslateIntent()` / `hasGoogleTranslate(context)` / `googleTranslate(context, text)`: builds, checks, and fires the Translate intent. WHY grouped: Translate is optional, so check-then-fire must stay together.

## Junior notes
- Never call `context.startActivity(intent)` for an implicit intent (one naming an action, not a class) without an availability check — that is the crash this file exists to prevent.
- `openWithDefaultBrowser` deliberately avoids the generic resolver in some paths; pass `allowResolverActivity` carefully to avoid the self-open loop.
- Translate availability delegates to `IntentUtils2` (Kotlin side); keep the Java/Kotlin pair in sync if the query logic changes.
