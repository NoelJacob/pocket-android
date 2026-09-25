# sync-pocket/src/main/graphql/local.graphqls

## What this is

This is a GraphQL schema + operations file for the sync layer: it declares the fields and shapes the app asks the server for. Apollo codegen reads it at build time and generates the typed request/response classes the sources use. Key entries include `saves`, `Tags`, `Friends`, `RecentFriends`, `AutoCompleteEmails`, `Groups`, `PostLikeStatus`, `PostRepostStatus`, `UserFollow`, `ConnectedAccounts`, `RecentSearches`, `Spocs`.

## How it fits

It is compiled by the Apollo Gradle plugin into generated models consumed by ClientApiSource (GraphQL transport) and, for v3 shapes, V3Source. In fdroid builds the local Express server reimplements these operations as REST under `/v3/` by reading the operation names, so the `.graphqls` files stay untouched while the wire format changes.

## Key pieces

Each entry below is an operation or schema type codegen turns into a typed class; the local server reimplements operation names as REST routes.

- `saves` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `Tags` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `Friends` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `RecentFriends` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `AutoCompleteEmails` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `Groups` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `PostLikeStatus` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `PostRepostStatus` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `UserFollow` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `ConnectedAccounts` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `RecentSearches` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `Spocs` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `AdzerkSpocs` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `AdzerkSpoc` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `HiddenSpoc` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `HiddenSpocs` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `Tweet` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `TweetEntities` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `TweetHashtagEntity` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `TweetUrlEntity` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- …and 16 more entries (this is a large schema file; search it by operation name).

## Junior notes

- Never hand-edit generated Apollo classes; change this file and rebuild so codegen stays the source of truth.
- Operation names are the contract with the local server (`POST /v3/<opname>`); renaming one means updating the server too.
