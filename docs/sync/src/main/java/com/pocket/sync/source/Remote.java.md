# sync/src/main/java/com/pocket/sync/source/Remote.java

## What this is

Describes how one Thing or Action talks to the server: which remote method/endpoint handles it and how to prepare the outgoing call (aliases, parameters, auth). The nested Method and RemoteCallDetails types carry the per-call specifics. Generated Spec code fills these in from the schema's remote/endpoint declarations, so hand-written code rarely touches them.

## How it fits

Remote transports (GraphQlSource, the V3 source) read these details to build actual HTTP calls, and toAlias maps client field names to whatever the server expects. If a new schema operation never reaches the network, its Remote details are the first place to look.

## Key pieces

- `Method/RemoteCallDetails` — the endpoint identity plus per-call payload details for one remote operation
- `toAlias/prepare` — name mapping and call setup applied before the request leaves the device

## Junior notes

- You almost never implement this by hand; it is generated from schema remote declarations, so fix the schema rather than patching these values.
