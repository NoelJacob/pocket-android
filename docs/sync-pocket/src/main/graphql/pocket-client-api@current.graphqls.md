# sync-pocket/src/main/graphql/pocket-client-api@current.graphqls

## What this is

This is a GraphQL schema + operations file for the sync layer: it declares the fields and shapes the app asks the server for. Apollo codegen reads it at build time and generates the typed request/response classes the sources use. Key entries include `ArticleMarkdown`, `Author`, `BatchWriteHighlightsResult`, `BulletedListElement`, `CachedImage`, `Collection`, `CollectionAuthor`, `CollectionPartnership`, `CollectionStory`, `CollectionStoryAuthor`, `CollectionsResult`, `CorpusItem`.

## How it fits

It is compiled by the Apollo Gradle plugin into generated models consumed by ClientApiSource (GraphQL transport) and, for v3 shapes, V3Source. In fdroid builds the local Express server reimplements these operations as REST under `/v3/` by reading the operation names, so the `.graphqls` files stay untouched while the wire format changes.

## Key pieces

Each entry below is an operation or schema type codegen turns into a typed class; the local server reimplements operation names as REST routes.

- `ArticleMarkdown` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `Author` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `BatchWriteHighlightsResult` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `BulletedListElement` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `CachedImage` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `Collection` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `CollectionAuthor` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `CollectionPartnership` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `CollectionStory` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `CollectionStoryAuthor` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `CollectionsResult` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `CorpusItem` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `CorpusItemAuthor` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `CorpusRecommendation` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `CorpusSearchConnection` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `CorpusSearchEdge` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `CorpusSearchHighlights` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `CorpusSearchNode` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `CorpusSlate` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- `CorpusSlateLineup` — declared operation/type in this file; generated classes carry this name into ClientApiSource call sites.
- …and 215 more entries (this is a large schema file; search it by operation name).

## Junior notes

- Uses Apollo GraphQL codegen (queries compiled from the `.graphql`/`.graphqls` files).
- Never hand-edit generated Apollo classes; change this file and rebuild so codegen stays the source of truth.
- Operation names are the contract with the local server (`POST /v3/<opname>`); renaming one means updating the server too.
