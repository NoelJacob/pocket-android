# graphql.config.yml

## What this is
This file configures GraphQL IDE tooling (the plugin pinned in `.idea/externalDependencies.xml`) with three projects: `prod` for the real client API schema and app queries, `test` for the `sync-pocket-android` unit-test schema, and `examples` for the `sync-gen` generator examples. Each project binds a schema file to the query files validated against it, and `figment.graphql` is included everywhere so the custom directives resolve. Only `prod` names a network endpoint; the rest are local-only.

## How it fits
When you edit any `.graphql`/`.graphqls` file under `sync-pocket/src/main/graphql/`, the IDE uses the `prod` project (schema `pocket-client-api@current.graphqls`) for autocomplete and error squiggles, with `https://client-api.getpocket.com` as the introspection endpoint (introspection disabled — schema comes from the file). Test queries under `sync-pocket-android/src/test/graphql/` validate against the smaller test schema instead, and generator examples against `examples.graphqls`. The `sync-parser`/`sync-gen` build pipeline is unaffected — this file is editor assistance, not codegen input.

## Key pieces
- **`prod` project (current client schema + all app queries + figment)** — exists so production query edits are checked against the real schema with directive-aware validation.
- **`test` project (`test.graphqls` + test queries)** — exists to keep unit-test fixtures isolated from production schema churn.
- **`examples` project (`examples.graphqls` + example queries)** — exists so generator sample schemas validate without pulling in the whole Pocket API.
- **`Pocket Graph – Production` endpoint (URL + `user-agent: JS GraphQL`, `introspect: false`)** — exists for tooling that wants endpoint metadata without allowing remote schema fetches to override the committed file.

## Junior notes
- `include` globs decide which files belong to which project — a new query directory must be added here or the IDE treats it as unowned plain text.
- Changing this file never changes generated code; if codegen output looks stale, the cause is in the schema or `sync-gen`, not here.
