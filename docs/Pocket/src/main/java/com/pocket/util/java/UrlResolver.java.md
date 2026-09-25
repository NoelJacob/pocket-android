# Pocket/src/main/java/com/pocket/util/java/UrlResolver.java
## What this is
Unwraps a short or redirecting URL to its final destination by following HTTP redirects one hop at a time. Each hop is reported to a `ResolveStepListener`, which can stop the walk early, and errors are reported instead of throwing.
For example, resolving a `bit.ly` link calls `onPreUrlResolve()` per hop until there is no next URL, then `onUrlFullyResolved()` delivers the full chain.

## How it fits
Created by `PocketUrlHandlerActivity` with the incoming `Intent` URL: `new UrlResolver(dataString, listener).resolveAsync()` runs the blocking walk on app background threads (`App.threads().async`), and the listener decides per hop whether to handle (save vs open-in-browser) the URL. `resolve()` is the same walk on the calling thread for callers that already background themselves.

## Key pieces
- `UrlResolver(url, stepListener)`: captures the original URL and required listener. WHY it exists: binds one resolution walk to its callbacks.
- `resolve()` / `resolveAsync()`: blocking redirect walk vs background submission. WHY they exist: `resolve()` does network I/O and must never run on the UI thread; `resolveAsync()` is the safe default.
- `getNextUrl(urlString)`: single redirect hop via `HttpURLConnection`. WHY it exists: isolates one follow-the-`Location`-header step.
- `ResolveStepListener`: `onPreUrlResolve(urls, next)` (return false to stop), `onUrlFullyResolved(urls)`, `onUrlResolveError(urls)`. WHY it exists: lets the handler inspect or bail at every hop instead of only seeing the end.

## Junior notes
- A `coroutine` (Kotlin background task) or `App.threads().async` is required for `resolve()`; calling it on the main thread freezes the UI and trips `NetworkOnMainThreadException`.
- Returning false from `onPreUrlResolve` halts the walk immediately, which is how the activity claims a URL mid-chain without fetching further hops.
