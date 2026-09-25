# pocket-local-server/server.js

## What this is
This is the complete local backend: a small Express server on `0.0.0.0:8080` that mimics just enough of Pocket's v3 REST API, login handshake, and article parser for end-to-end development with one canned Wikipedia article. Every route answers with the wire shapes proven by the `sync-pocket` unit tests (`PocketRemoteSourceShould`) and the `V3Source` protocol docs. A request logger prints each call's method, path, and body keys, and a catch-all route logs loudly and returns `{}` so unexpected app traffic surfaces in the terminal instead of failing silently.

## How it fits
Start it with `node --watch server.js` from `pocket-local-server/`, then build the app's `developDebug` variant, which points `PocketServer`, `V3Source`, the ClientApi URL, and the parser at `http://10.0.2.2:8080`. The verified loop is: fake-login via the `pocket://auth` deep link → share the Wikipedia URL → app POSTs an `add` action to `/v3/send` → list screens read back `/v3/fetch` (or `/v3/get`) → tapping the card loads `/parser` HTML into ArticleView. The emulator's `10.0.2.2` maps to your machine's localhost, and cleartext HTTP is allowed by the app's network security config.

## Key pieces
- **Request-log middleware (`METHOD path keys=…`)** — the protocol oracle: shows exactly what the app sends (operation names, body keys) so new `/graphql` operations can be reimplemented as REST under `/v3/`.
- **`GET/POST /v3/guid → {"guid":"local-test-guid"}`** — anonymous session bootstrap the sync engine calls first; every response carries an `X-Source: Pocket` header.
- **`POST /v3/send_guid → {"action_results":[true]}`** — acknowledges the guid handshake with one success result.
- **`POST /v3/send` (per-action `[true]` array)** — the save path: `ItemRepository` POSTs an `add` action here; the server parses `actions` (or `action`), tolerating both object and JSON-string forms, and returns one `true` per action. Example: `curl -X POST localhost:8080/v3/send -H 'Content-Type: application/json' -d '{"actions":[{"action":"add","url":"https://en.wikipedia.org/wiki/Pocket_(service)"}]}'` → `{"action_results":[true]}`.
- **`POST /v3/getAfterLogin` (account + `premium_status:true`)** — the login round-trip for `loginWithAccessToken`; returns a minimal account (`user_id:"1"`, `username:"local"`, `email:"local@example.com"`) with premium on so `PocketCache` persists a logged-in premium session.
- **`ALL /v3/get*` (single-item `list` map keyed by `"1"`)** — list read-back in classic v3 shape (`item_id`, `resolved_id`, `given_url`, `resolved_url`, `resolved_title`, `favorite`, `status`, `is_article`, `has_video`, `is_index`).
- **`ALL /v3/fetch` (paginated Fetch shape)** — the same Wikipedia item as a `list[]` array with `status`, `total`, `remaining_items`, `remaining_chunks`, plus `time_added`/`time_updated`; honors paging params when the app sends them.
- **`ALL /parser` (`{article, resources:[], item}`)** — pre-parsed article HTML: the `ARTICLE_PARAS` paragraphs joined as `<p>` markup with empty resources and an `item` carrying `given_url`, `time_added`, and `idkey`. There is no client-side parser (in-app jsoup is TTS/image-matching only), so this endpoint is what ArticleView actually renders.
- **`ALL /graphql → {"data":{}}`** — a deliberate stub so ClientApi traffic is observable in the request log instead of connection-refused; real operations get reimplemented as REST under `/v3/` by reading the logged query names.
- **Catch-all `ALL /.*/` (logs `UNHANDLED …` + `{}`)** — exists so any unimplemented call shows its full body (first 300 chars) in the server terminal, making missing endpoints self-reporting.

## Junior notes
- `app.all(...)` (Express) means the route answers every HTTP method — the app mixes GET and POST against these paths, so method-specific handlers would miss calls.
- Coroutines (Kotlin's background-task mechanism) perform these calls off the main thread; a slow or stopped server shows up as an eternal spinner in the app, while the truth is visible in the server terminal log.
