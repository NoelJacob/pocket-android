# sync-pocket-android/src/test/graphql/queries.graphql

## What this is

The operations file driving codegen or transport tests: it declares the GraphQL operations whose generated classes the tests exercise. GraphQL is the query language both the schema DSL and the client API use here.

## How it fits

Parsed by SpecParser (schemas) or QueryParser (operations) into figment models, then fed to generators; the test pair (test.graphqls plus queries.graphql) backs GraphQlSourceTest and the generated test API.

## Entries

- Operations: GraphQlQueryReturnsScalar, GraphQlQueryReturnsThing, GraphQlQueryWithArgs
- 26 lines
