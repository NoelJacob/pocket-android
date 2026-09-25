# Pocket/src/main/java/com/pocket/sdk/http/CookieDelegate.java

## What this is
Bridges the app's network cookie jar and Android's `WebView` cookie store, mainly for the site-login feature where article pages need the user's login cookies. It can read cookies for a URL, push network cookies into the `WebView` store (extending their expiry), attach cookies to outgoing requests, and flush the store. On logout it wipes `WebView` cookies so the next user starts clean.

## How it fits
Singleton created by Hilt DI with the shared `HttpClientDelegate` and the app thread pool; it registers for app-lifecycle events to handle logout. `extendCookies(url)` first hits the URL with the shared HTTP client (so the jar holds fresh cookies), then copies them into `CookieManager`; `addCookiesToRequest(...)` goes the other direction for raw requests. Consumers are the login-list flow and anything rendering authenticated web content; `sync()` persists the store after writes.

## Key pieces
- `getCookiesString(url)` — returns the `WebView` cookie header for a URL. Exists as the read path for code that needs "what would the browser send?".
- `extendCookies(url)` / `extendCookies(url, cookies)` — fetches fresh cookies then writes each one into `CookieManager` with a far-future expiry, preserving domain/path/secure attributes. Exists because `WebView` and the network client keep separate jars that must be reconciled.
- `addCookiesToRequest(request, client)` — copies matching cookies onto an outgoing request. Exists for non-`WebView` fetches that still need the logged-in session.
- `sync()` — flushes `CookieManager` asynchronously. Exists because writes are otherwise held in memory; the async wrapper avoids blocking the caller (older Android flushed on the calling thread).
- `onLogoutStarted()` — clears `WebView` cookies on logout. Exists to prevent session leakage between users.

## Junior notes
- `CookieManager` calls generally expect the UI thread and a prior `init()`; this class's `CookieSyncManagerCompat` shim handles the version differences, so don't bypass it.
- The `expires=2049` rewrite is deliberate session-extension, not a bug; site logins stay valid inside the app longer than the raw cookie lifetime.
