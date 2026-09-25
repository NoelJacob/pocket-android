# Pocket/src/main/java/com/pocket/app/PocketUrlHandlerActivity.java
## What this is
The exported router for VIEW intents on Pocket and email-tracking URLs (an exported Activity can be launched by other apps/the system, so its inputs are untrusted). It decides per URL whether to save (`getpocket.com/save` -> `AddActivity`), open natively in the app (`DeepLinks.Parser` -> in-app intent, recording the deep link), follow email redirects step by step, or fall back to the external browser. It shows no UI except a resolving toast.
## How it fits
The system delivers link taps here; `handleUrl` is the decision point. Save URLs go to `AddActivity`; native links become intents started via `startIntent` (with `AppOpen` deep-link + referrer recorded for attribution); email hosts go through `resolveEmailUrl`, which follows redirects checking `handleUrl` at each hop; anything unrecognized opens in the browser. `isInterceptingDisabled` honors `no_app_intercept=1` opt-outs.
## Key pieces
- `onCreate`: null-data guard, email-host branch, native-handle branch, browser fallback; WHY email links get their own path is they redirect through trackers before revealing the real Pocket URL.
- `handleUrl`: save-URL check first, then `DeepLinks.Parser.parseLinkFromExternalApplication`; on success records `AppOpen` deep link + referrer so the session is attributable.
- `resolveEmailUrl`: resolves redirect chains off-thread, attempting native handling per hop and falling back to the browser on errors; WHY per-hop handling is the final URL may differ entirely from the clicked one.
- `startAddActivity` / `startBrowser` / `startIntent`: the three exits; `StartActivityRunnable` marshals background-resolver results back to the activity safely, accounting for the user leaving mid-resolve.
## Junior notes
- Exported means hostile input: never trust extras, query params, or redirect targets here; the existing code parses defensively and this must stay that way.
- The activity finishes as part of routing; do not add UI state to it, and always check `isInterceptingDisabled` before adding new interception behavior so the opt-out keeps working.
