# sync-pocket/src/main/java/com/pocket/sdk/api/source/ClientApiSource.kt

## What this is

ClientApiSource is the transport for Pocket's newer GraphQL Client API (`https://client-api.getpocket.com`): it sends operations built from the `.graphql` files and returns full results for things whose remote style is CLIENT_API. It only claims syncables marked with that style via `isSupported`.

## How it fits

It sits beside V3Source inside PocketRemoteSource, which routes traffic between them. Requests go out through ClientApiHandler, which does the actual HTTP POST; responses come back as spec `Thing` updates the same way v3 results do.

## Key pieces

- `ClientApiSource` (class, line 163) — The Client API is the start of the next generation of API development at Pocket. It is a Federated Apollo GraphQL Gateway that clients can connect to and receiv
- `syncFull` (fun, line 172) — entry point other code calls; see callers for context.
- `setCredentials` (fun, line 175) — entry point other code calls; see callers for context.
- `isSupported` (fun, line 179) — entry point other code calls; see callers for context.

Concrete endpoints referenced here:

- `https://api.getpocket.com/graphql`

## Junior notes

- Uses Apollo GraphQL codegen (queries compiled from the `.graphql`/`.graphqls` files).
- Types here come from Apollo codegen: if a symbol is missing after editing GraphQL, rebuild before assuming a bug.

Names you will also see here: `ClientApiHandler`, `Credentials`, `Endpoint`, `PocketRemoteStyle`, `EclecticHttp`, `Action`.
