# sync-gen/examples/graphql/examples.graphqls

## What this is

The schema file driving codegen or transport tests: it declares types, scalars, and endpoint wiring. GraphQL is the query language both the schema DSL and the client API use here.

## How it fits

Parsed by SpecParser (schemas) or QueryParser (operations) into figment models, then fed to generators; the test pair (test.graphqls plus queries.graphql) backs GraphQlSourceTest and the generated test API.

## Entries

- Declared types: Dangerous, Timestamp, Mutation, ThingExample, IdentifiableByValue, IdentifiableByIdentifiableThing, IdentifiableByNonIdentifiableThing, NonIdentifiable, HasDangerousValue, Mutation, IntegerEnum, BasicEnum, VarietyExample, InterfaceExample
- 146 lines
