# Pocket/src/main/java/com/pocket/sdk/api/PocketServer.java

## What this is
Holds the base URLs the app talks to: the Pocket API, the article-view (parser) endpoint, and the Snowplow analytics collector plus its post path. On internal/team builds these resolve through a `DevConfig` so testers can point the app at dev servers; on production builds they are fixed constants. Every network call ultimately reads its host from here.

## How it fits
Injected once as a singleton; `HttpClientDelegate` reads `api()` to decide which requests get full body logging, and the sync/analytics stacks read the API and Snowplow URLs when building requests. `DevConfig` values come from the `dcfig_` preferences group, which the internal settings screen edits. Tracing a save: UI queues the action locally, the sync layer POSTs it to `PocketServer.api()`, and any analytics event from that screen POSTs to `snowplowCollector()` + `snowplowPostPath()`.

## Key pieces
- `API_PRODUCTION` / `ARTICLE_VIEW` / `PERM_LIBRARY` — the production endpoint constants (API host, parser address, library loader). Exist as the fallback every build uses unless overridden.
- `PocketServer(AppMode mode, Preferences prefs)` — constructor that picks fixed production URLs or live `DevConfig` URLs based on whether this is an internal build. Exists so release builds can never accidentally point at a dev server.
- `api()` / `articleView()` / `snowplowCollector()` / `snowplowPostPath()` — the getters the rest of the app reads. Exist so callers never hardcode hosts.
- `DevConfig` — preferences-backed overrides for API host, parser host, and Snowplow collector (options 0/1/2 select prod, dev, or a custom/micro host). Exists to let QA switch backends without rebuilding.

## Junior notes
- Hilt DI (constructor parameters provided automatically) creates this once; `mode` (production vs internal vs dev) is what unlocks the dev-server switching.
- Internal builds default some endpoints at local test addresses in debug; if network calls hit localhost on a device, check `DevConfig` prefs first.
