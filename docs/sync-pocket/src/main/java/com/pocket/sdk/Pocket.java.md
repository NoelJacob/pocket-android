# sync-pocket/src/main/java/com/pocket/sdk/Pocket.java

## What this is

Pocket is the central entry point of the sync-pocket SDK: apps build one `Pocket` instance and use it to log users in, sync saved items, and read account state. It wires together the network sources, the local spec applier/deriver, and the login/logout flow behind a single facade.

## How it fits

It is created at app startup (see the app's Hilt module providing the singleton) and consumed by repositories such as ItemRepository and UserRepository. It delegates network work to PocketRemoteSource (which fans out to V3Source and ClientApiSource), applies server results through Applier/Deriver, and exposes nested helpers `UserApi`, `SetupStep`, `AuthenticationExtras`, and `Config/Builder` for login and configuration.

## Key pieces

- `Pocket` (class, line 90) — A Pocket that can work locally, offline and syncs with Pocket's API.
- `SetupStep` (interface, line 301) — core type of this file; callers reference it by name.
- `UserApi` (class, line 315) — A helper for managing which user is logged in.
- `LoginEvent` (enum, line 458) — core type of this file; callers reference it by name.
- `OnLoginEventListener` (interface, line 465) — core type of this file; callers reference it by name.
- `source` (fun, line 140) — Use this method rather than accessing {@link #source} directly,
- `updateCredentials` (fun, line 168) — entry point other code calls; see callers for context.
- `spec` (fun, line 174) — entry point other code calls; see callers for context.
- `sync` (fun, line 179) — entry point other code calls; see callers for context.
- `syncRemote` (fun, line 184) — entry point other code calls; see callers for context.
- `syncLocal` (fun, line 189) — entry point other code calls; see callers for context.
- `syncActions` (fun, line 194) — entry point other code calls; see callers for context.
- `blockLogins` (fun, line 199) — Throw an exception if the thing is a login request.
- `subscribe` (fun, line 210) — entry point other code calls; see callers for context.
- `bindLocal` (fun, line 215) — entry point other code calls; see callers for context.

## Junior notes

- Uses OkHttp HTTP client.
- Thread-safety is explicit here (`synchronized`/`volatile`): do not call these paths from the main thread and do not add unsynchronized mutable state.

Names you will also see here: `ApiException`, `AppInfo`, `Credentials`, `DeviceInfo`, `PocketAuthType`, `AuthMethod`.
