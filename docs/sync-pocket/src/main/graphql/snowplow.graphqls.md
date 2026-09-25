# sync-pocket/src/main/graphql/snowplow.graphqls

## What this is

This is a GraphQL schema + operations file for the sync layer: it declares the fields and shapes the app asks the server for. Apollo codegen reads it at build time and generates the typed request/response classes the sources use. Key entries include `SnowplowEntity`, `AdEntity_1_0_0`, `ApiUserEntity_1_0_1`, `ContentEntity_1_0_0`, `FeatureFlagEntity_1_0_0`, `ReportEntity_1_0_0`, `UiEntity_1_0_3`, `UserEntity_1_0_1`, `SlateLineupEntity_1_0_0`, `SlateEntity_1_0_0`, `RecommendationEntity_1_0_0`, `ApiUserEntity_1_0_0`.

## How it fits

It is compiled by the Apollo Gradle plugin into generated models consumed by ClientApiSource (GraphQL transport) and, for v3 shapes, V3Source. In fdroid builds the local Express server reimplements these operations as REST under `/v3/` by reading the operation names, so the `.graphqls` files stay untouched while the wire format changes.

## Key pieces

Each entry below is an operation or schema type codegen turns into a typed class; the local server reimplements operation names as REST routes.

- `SnowplowEntity` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `AdEntity_1_0_0` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `ApiUserEntity_1_0_1` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `ContentEntity_1_0_0` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `FeatureFlagEntity_1_0_0` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `ReportEntity_1_0_0` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `UiEntity_1_0_3` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `UserEntity_1_0_1` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `SlateLineupEntity_1_0_0` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `SlateEntity_1_0_0` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `RecommendationEntity_1_0_0` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `ApiUserEntity_1_0_0` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `UiEntity_1_0_1` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `UiEntity_1_0_2` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `UserEntity_1_0_0` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `ContentOpenDestination` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `ContentOpenTrigger` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `EngagementType` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `ImpressionComponent` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `ImpressionRequirement` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- …and 6 more entries (this is a large schema file; search it by operation name).

## Junior notes

- Never hand-edit generated Apollo classes; change this file and rebuild so codegen stays the source of truth.
- Operation names are the contract with the local server (`POST /v3/<opname>`); renaming one means updating the server too.
