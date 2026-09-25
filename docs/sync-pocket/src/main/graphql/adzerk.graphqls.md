# sync-pocket/src/main/graphql/adzerk.graphqls

## What this is

This is a GraphQL schema + operations file for the sync layer: it declares the fields and shapes the app asks the server for. Apollo codegen reads it at build time and generates the typed request/response classes the sources use. Key entries include `getAdzerkDecisions`, `AdzerkPlacement`, `AdzerkUser`, `AdzerkDecision`, `AdzerkContent`, `AdzerkContentData`, `AdzerkPlacementName`, `AdzerkNetworkId`, `AdzerkSiteId`, `AdzerkAdType`, `AdzerkZoneId`.

## How it fits

It is compiled by the Apollo Gradle plugin into generated models consumed by ClientApiSource (GraphQL transport) and, for v3 shapes, V3Source. In fdroid builds the local Express server reimplements these operations as REST under `/v3/` by reading the operation names, so the `.graphqls` files stay untouched while the wire format changes.

## Key pieces

Each entry below is an operation or schema type codegen turns into a typed class; the local server reimplements operation names as REST routes.

- `getAdzerkDecisions` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `AdzerkPlacement` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `AdzerkUser` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `AdzerkDecision` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `AdzerkContent` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `AdzerkContentData` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `AdzerkPlacementName` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `AdzerkNetworkId` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `AdzerkSiteId` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `AdzerkAdType` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `AdzerkZoneId` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.

## Junior notes

- Never hand-edit generated Apollo classes; change this file and rebuild so codegen stays the source of truth.
- Operation names are the contract with the local server (`POST /v3/<opname>`); renaming one means updating the server too.
