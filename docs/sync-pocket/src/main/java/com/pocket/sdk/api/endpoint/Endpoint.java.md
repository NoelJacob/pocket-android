# sync-pocket/src/main/java/com/pocket/sdk/api/endpoint/Endpoint.java

## What this is

Endpoint is the base class for typed API calls: it pairs request parameters with response parsing and uniform error mapping into ApiException. Concrete endpoints extend it instead of hand-rolling HTTP boilerplate.

## How it fits

Endpoint subclasses such as AdzerkEndpoint are executed with the shared HTTP client from sources like AdzerkSource; EndpointStrings holds the shared parameter names they use.

## Key pieces

- `Endpoint` (class, line 25) — Stateless execution of a request to the Pocket V3 Api.
- `ResponseStreamer` (interface, line 110) — core type of this file; callers reference it by name.
- `execute` (fun, line 38) — Executes a request to v3 and parses and returns the response as a json object.
- `execute` (fun, line 51) — Executes a request to v3.
- `newExceptionFromResponse` (fun, line 86) — entry point other code calls; see callers for context.
- `newExceptionFromResponse` (fun, line 99) — entry point other code calls; see callers for context.
- `readResponse` (fun, line 119) — Process the InputStream of the response and return a result. If any problems are encountered, throw an exception.
- `getXHeaderValue` (fun, line 122) — entry point other code calls; see callers for context.
- `hash` (fun, line 127) — entry point other code calls; see callers for context.
- `newExceptionFromResponse` (fun, line 132) — entry point other code calls; see callers for context.
- `a401OnThisEndpointMeanRevokedToken` (fun, line 151) — Is this request being made to an endpoint that returns 401s normally instead of indicating a revoked token?
- `guid` (fun, line 172) — entry point other code calls; see callers for context.

Concrete endpoints referenced here:

- `https://pocket.slack.com/archives/C03C9QQE0/p1503082531000444?thread_ts=1503081169.000131&cid=C03C9QQE0`
- `https://pocket.slack.com/archives/C067Y2396/p1546434942001500`

## Junior notes

- Uses Jackson JSON trees (`ObjectNode`).

Names you will also see here: `EclecticHttp`, `EclecticHttpRequest`, `JsonUtil`.
