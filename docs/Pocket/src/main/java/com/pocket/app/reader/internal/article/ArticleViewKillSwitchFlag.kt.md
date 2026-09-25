# Pocket/src/main/java/com/pocket/app/reader/internal/article/ArticleViewKillSwitchFlag.kt
## What this is
This is a server-controlled kill switch that disables Article view app-wide when the article parser is broken. Off by default; when the server flag `perm.android.disableArticleView` turns on, the app routes articles to Original Web instead. It exists so a backend outage can be mitigated without shipping a new build.
## How it fits
Created once as a `@Singleton` via Hilt DI, it fetches the flag from `ServerFeatureFlags` at startup and caches it in per-user `Preferences`. `DestinationHelper.getDestination` checks `isEnabled` before choosing `ARTICLE`, and `ArticleViewModel` paths that require parsed content respect it the same way.
## Key pieces
- `isEnabled` — reads the cached per-user boolean; the network fetch in `init` only updates the cache. Caching is what makes the kill switch work offline and instantly on next launch.
- `FLAG = "perm.android.disableArticleView"` — the server-side flag name; renaming either side breaks the switch silently.
## Junior notes
- `prefs.forUser` scopes the value to the logged-in account, so the switch follows the user across devices rather than sticking to one install.
