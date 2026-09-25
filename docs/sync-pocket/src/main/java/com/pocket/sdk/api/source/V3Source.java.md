# sync-pocket/src/main/java/com/pocket/sdk/api/source/V3Source.java

## What this is

V3Source is the transport for Pocket's v3 REST API: it turns sync `Action` objects into JSON and POSTs them to endpoints like `https://api.getpocket.com/v3/send` and `/v3/send_guid`. It implements `SynchronousSource`, `FullResultSource`, and `LimitedSource`, and only handles things whose remote style is V3.

## How it fits

PocketRemoteSource owns a V3Source alongside the GraphQL ClientApiSource and routes each syncable to the right one. Its output feeds Applier/Deriver in the spec package; `toV3ActionJson` is the shared serializer other code uses to build v3 action payloads. In fdroid builds the base URL points at the local server (`http://10.0.2.2:8080`).

## Key pieces

- `V3Source` (class, line 239) — core type of this file; callers reference it by name.
- `setCredentials` (fun, line 264) — entry point other code calls; see callers for context.
- `setMaxActions` (fun, line 273) — See {@link Get#maxActions}
- `sync` (fun, line 286) — entry point other code calls; see callers for context.
- `syncFull` (fun, line 296) — entry point other code calls; see callers for context.
- `createRequest` (fun, line 471) — Builds an {@link com.pocket.sdk.api.endpoint.Endpoint.Request} for a thing or action, determines the right url, attaches all parameters and settings.
- `requiresGuid` (fun, line 533) — entry point other code calls; see callers for context.
- `attachCredentials` (fun, line 541) — entry point other code calls; see callers for context.
- `isAuthed` (fun, line 564) — entry point other code calls; see callers for context.
- `toV3ActionJson` (fun, line 589) — Reformat to fit into the Pocket "action" syntax, since {@link Action} has some slight differences.
- `statusOfRemoteActionFailure` (fun, line 604) — This remote action threw an {@link ApiException}, decide on what {@link Status} should be returned.

Concrete endpoints referenced here:

- `http://10.0.2.2:8080`
- `https://api.getpocket.com/`
- `https://api.getpocket.com/v3/send`
- `https://api.getpocket.com/v3/send_guid`

## Junior notes

- Uses Jackson JSON trees (`ObjectNode`).
- Thread-safety is explicit here (`synchronized`/`volatile`): do not call these paths from the main thread and do not add unsynchronized mutable state.

Names you will also see here: `ApiException`, `Credentials`, `Endpoint`, `Request`, `Modeller`, `PocketAuthType`.
