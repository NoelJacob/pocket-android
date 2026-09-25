# sync-pocket/src/main/java/com/pocket/sdk/api/endpoint/ClientApiHandler.kt

## What this is

ClientApiHandler is the HTTP layer for GraphQL calls: it POSTs the operation JSON built by ClientApiSource to the client-api endpoint and hands the response stream back through a callback. It implements `GraphQlSource.HttpHandler`.

## How it fits

It is owned by ClientApiSource and given the shared EclecticHttp client. In fdroid builds the endpoint resolves to the local server so operations are served as REST under `/v3/` instead of real GraphQL on the wire.

## Key pieces

- `ClientApiHandler` (class, line 14) — core type of this file; callers reference it by name.
- `execute` (fun, line 18) — entry point other code calls; see callers for context.

Concrete endpoints referenced here:

- `http://10.0.2.2:8080/graphql`
- `https://client-api.getpocket.com`

## Junior notes

- Uses Apollo GraphQL codegen (queries compiled from the `.graphql`/`.graphqls` files).
- Uses Jackson JSON trees (`ObjectNode`).
- Types here come from Apollo codegen: if a symbol is missing after editing GraphQL, rebuild before assuming a bug.

Names you will also see here: `EclecticHttp`, `EclecticHttpRequest`, `GraphQlSource`, `JsonUtil`.
