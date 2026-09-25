# Pocket/src/main/java/com/pocket/app/settings/UserAgent.java
## What this is
This manages the HTTP User-Agent strings Pocket sends when downloading articles and rendering the reader. It keeps two persisted strings — the real mobile agent captured from a WebView on the device, and a fake desktop agent derived from it — and preferred() returns one based on the user's "use mobile agent" setting. Faking desktop is what makes some sites serve their full article HTML instead of a stripped mobile page.
## How it fits
A Hilt singleton injected into download/parsing code; the "Mobile User-Agent" toggle in PrefsFragment flips AppPrefs.USE_MOBILE_AGENT, which preferred() reads. reload(context) refreshes both stored agents from a live WebView (falling back to hard-coded FAIL_SAFE strings when WebView is unavailable) and should run early, since agents are read on every fetch.
## Key pieces
- `mobile()` / `desktop()`: the two stored agents; desktop is the mobile string with the OS token swapped for FAKE_DESKTOP_OS (X11 Linux) so servers treat it as desktop Chrome.
- `preferred()`: the single call-site API — picks mobile vs desktop from the user preference.
- `reload(context)`: creates a WebView to read its default agent, derives and saves both variants; WHY the failsafes exist — some devices/threads cannot create a WebView (it must run on the UI thread and can throw).
## Junior notes
- WebView instantiation must happen on the main thread and is expensive — reload is a rare operation (app start/migration), never per-request.
- The REVIEW comment is honest: hard-coded agent strings rot as Chrome versions move; prefer the live-captured values and treat FAIL_SAFE_* as last-resort only.
