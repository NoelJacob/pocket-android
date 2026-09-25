# sync-pocket-android/src/test/java/com/pocket/sync/source/protocol/graphql/GraphQlSourceTest.kt

## What this is

GraphQlSource round-trip proofs with a mocked HttpHandler: thing return (nested thing payload), scalar return, error, and transport-failure cases over canned JSON bodies assert parsed results and SyncException/Status handling. handlerMock fakes the wire so no network is involved. It is the executable contract that GraphQL 200-with-errors and HTTP failures both surface correctly.

## How it fits

Guards GraphQlSource plus GraphQlErrors parsing against the test operations (GraphQlQueryReturns*); run on any transport or error-mapping change.

## Key pieces

- `thing return / scalar return` — data payloads parsing into the expected Things and scalars
- `error / failure cases` — GraphQL errors-array and transport failures mapping to typed outcomes
- `error return / bad server result` — further cases in this file covering adjacent behavior of the same contract

## Junior notes

- Canned bodies must track the real API's error shapes: a new server error format needs a new case here, not just handling code.
