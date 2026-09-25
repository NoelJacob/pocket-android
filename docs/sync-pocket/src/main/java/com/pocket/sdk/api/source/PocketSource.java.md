# sync-pocket/src/main/java/com/pocket/sdk/api/source/PocketSource.java

## What this is

PocketSource defines the `Source` contract every sync backend implements: sync a thing with actions, report which syncables it supports. It is the seam that lets Pocket treat V3, GraphQL, and test fakes interchangeably.

## How it fits

Pocket holds sources behind this interface, and tests substitute fake implementations of it. V3Source, ClientApiSource, and PocketRemoteSource all implement or extend this contract.

## Key pieces

- `PocketSource` (class, line 15) — A Pocket Source implementation that can work and persist locally and is linked with the Pocket v3 API.
- `setCredentials` (fun, line 28) — Set what credentials will be used on future calls to the v3 api.

## Junior notes

- Read the file top to bottom once; it is small and its declaration order follows its logic.

Names you will also see here: `Pocket`, `Credentials`, `PocketSpec`, `AppSource`, `Publisher`, `ThreadPools`.
