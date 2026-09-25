# sync-pocket-android/src/test/java/com/pocket/sdk/api/source/PocketV3SourceTest.java

## What this is

The V3 transport contract suite: its own javadoc says it tests every endpoint-based Thing and every Action on PocketRemoteSource, verifying connect, send, receive, and parse against the Pocket API. The single everything test (~786 lines of fixture and assertion) walks the whole surface in one go. This is the wire-compat proof for the legacy API family.

## How it fits

Runs with scripted HTTP (no live network); a failure means the transport or generated parsing drifted from the API's actual shapes. ClientApiSourceTest covers the GraphQL family the same way.

## Key pieces

- `everything` — the full endpoint-Thing plus Action walkthrough over scripted API responses

## Junior notes

- One giant test means one giant failure log: read the first assertion failure, the rest are usually cascading state.
