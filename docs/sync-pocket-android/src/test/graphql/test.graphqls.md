# sync-pocket-android/src/test/graphql/test.graphqls

## What this is

The schema file driving codegen or transport tests: it declares types, scalars, and endpoint wiring. GraphQL is the query language both the schema DSL and the client API use here.

## How it fits

Parsed by SpecParser (schemas) or QueryParser (operations) into figment models, then fed to generators; the test pair (test.graphqls plus queries.graphql) backs GraphQlSourceTest and the generated test API.

## Entries

- Declared types: Timestamp, Dangerous, DeepCollectionsTest, SomethingWithIdentity, SomethingWithoutIdentity, SomethingElseWithoutIdentity, Depth1, Depth2, Depth3, ReactiveThing, ReactiveTarget, HasIdentityWithThing, HasIdentityWithNonIdThing, HasComplexIdentityWithThing
- 373 lines
