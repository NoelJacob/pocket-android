# Pocket/src/main/java/com/pocket/app/AppOpen.kt
## What this is
A tiny Hilt-singleton `AppLifecycle` that remembers how the app was most recently opened: the deep-link URL string and the Android referrer Uri. Any screen or analytics call can read these two vars instead of threading intent extras through navigation. Both are cleared in `onUserGone`, when the user leaves the app entirely.
## How it fits
`PocketUrlHandlerActivity` fills it in when it routes an external Pocket link into the app (deep link plus referrer). Downstream readers use the values to attribute the session (e.g. which shared link launched the app). It self-registers with the dispatcher in `init`.
## Key pieces
- `deepLink`: the URL string that opened the app; WHY a plain `var` is that it is write-once-per-open, read-many, cleared-on-exit state, not reactive UI state.
- `referrer`: the Android referrer Uri (which app/link sent the user); nullable because most opens have none.
- `onUserGone` override: nulls both; WHY clearing matters is attribution must not leak from one app session into the next.
## Junior notes
- These are plain mutable vars with no thread confinement; they are written from the URL-handler activity and read on the UI thread, so do not access them from background workers without posting.
- A null `deepLink` is the normal case (launcher icon opens); always null-check before attributing anything to a link.
