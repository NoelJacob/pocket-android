# sync/src/main/java/com/pocket/sync/source/protocol/graphql/GraphQlErrors.kt

## What this is

Parses GraphQL error payloads per the GraphQL spec's Errors section (message, locations, path, extensions) into typed GraphQlError values, with helpers for inspecting them. GraphQL famously returns HTTP 200 with an errors array alongside partial data, so without this helper a transport could mistake a failed field for an empty one. The kdoc even shows the raw JSON shape for reference.

## How it fits

GraphQlSource consults these after every response to decide whether the data is trustworthy, and SyncExceptions built from them carry structured failure info up to AppSource.onRemoteResult instead of a bare HTTP code.

## Key pieces

- `GraphQlError` — one parsed spec-compliant error entry with message, location, path, and extensions
- `parse/inspection helpers` — turn the raw errors array into checkable values the transport can branch on

## Junior notes

- GraphQL errors ride inside 200 responses, so status-code checks alone miss them; always consult the parsed errors array.
- Not every GraphQL error is user-facing, so transports must decide which ones become user messages versus quiet retries.
