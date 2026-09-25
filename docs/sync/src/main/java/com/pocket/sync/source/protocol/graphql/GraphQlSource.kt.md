# sync/src/main/java/com/pocket/sync/source/protocol/graphql/GraphQlSource.kt

## What this is

The transport that actually performs GraphQL syncs: it takes a GraphQlSyncable (a Thing-as-query or Action-as-mutation), serializes it to a GraphQL request document, fires it through an injected HttpHandler, then parses data plus errors back into typed results. The HttpHandler seam keeps HTTP client details out of the engine so tests can fake the wire with canned JSON. Only GraphQlSyncable operations are supported here, not legacy V3 calls.

## How it fits

AppSource's remote side uses this for all client-API traffic: Pocket screens sync saves and lists through it, and GraphQlSourceTest proves the round trip with mocked handler responses. GraphQlSupport supplies the per-operation documents.

## Key pieces

- `syncFull` — the blocking request/response cycle for one query or mutation, returning parsed data plus errors
- `HttpHandler` — the injectable HTTP seam (execute with request JSON, respond with body stream) that tests fake and production fills with OkHttp
- `GraphQlResult` — the parsed outcome pairing returned Things with any spec-compliant errors

## Junior notes

- Inject HttpHandler rather than hard-coding HTTP: every test in GraphQlSourceTest works because the wire is a fake.
- The REVIEW note is honest: GraphQL errors are not guaranteed user-safe prose, so think before surfacing them in UI.
