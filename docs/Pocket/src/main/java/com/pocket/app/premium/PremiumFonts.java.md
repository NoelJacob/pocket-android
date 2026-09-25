# Pocket/src/main/java/com/pocket/app/premium/PremiumFonts.java

## What this is
Downloader, cache, and provider for the Premium-only reader typefaces (Ideal Sans, Inter, IBM Plex, Sentinel, Tiempos, Vollkorn, Whitney, Zilla Slab with their italic/bold variants). Free users never fetch these.

## How it fits
A `@Singleton` and `AppLifecycle` observer: on login and on user-present it kicks off `initFonts`, which checks the server for font bundles and downloads them into the app-private `premiumfonts/` directory. The Reader asks for a `Typeface` via `get(Font)`; listeners (`FontsListener`) are notified when fonts become ready or unavailable.

## Key pieces
- `Font` enum — WHY: the full catalog of premium faces with their file names; the Reader maps its font setting onto these values.
- `get(font)` — WHY: returns the cached `Typeface`, loading from disk on first use; returns null (not a crash) when the file is missing so the Reader falls back.
- `initFonts` / download path — WHY: fetches the CSS/file manifests over `HttpClientDelegate` on background `AppThreads`, gated on premium status (`PocketCache`) and network availability.
- `FontsListener` (`onFontsReady` / `onFontsUnavailable`) — WHY: lets the Reader refresh its font picker the moment downloads finish.
- App-private `fontsDir` — WHY: fonts are injected into article HTML, so they must live where no other app could tamper with them (WebView code injection risk).

## Junior notes
- Never move `fontsDir` outside the app sandbox (e.g. external storage) — the comment in the constructor is a security requirement, not a preference.
- `downloading` is an `AtomicBoolean` because init can be triggered from multiple lifecycle callbacks; concurrent downloads are coalesced, not queued.
