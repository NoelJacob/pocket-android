# sync-parser/src/commonMain/kotlin/com/pocket/sync/Figments.kt

## What this is

The query facade over a parsed schema: Figments wraps the raw FigmentsData and exposes every definition sorted by name, filtered by kind (things, actions, syncables, values, enums, remotes, auths, varieties), plus lookups (get throws when missing, find returns null) and schema-level answers (baseAction, remoteBases, defaultRemote, defaultAuth, reactives, endpoints). Codegen asks questions here instead of walking raw data, so generation logic reads as what-it-wants, not how-to-find-it. Figment is the historical name for the in-memory schema representation.

## How it fits

sync-gen drives almost entirely through this: generators enumerate things/actions/values/enums to emit and consult reactives/endpoints for special handling. UsageModeCalculator also starts from these listings when deciding what to generate.

## Key pieces

- `things/actions/syncables/values/enums/remotes/auths/varieties` — kind-filtered, name-sorted views of every schema definition
- `get/find` — by-name lookup, throwing versus null when absent
- `baseAction/remoteBases/defaultRemote/defaultAuth` — schema-level answers about action inheritance and default endpoints
- `reactives/endpoints` — which Things need derive support and which operations hit the network

## Junior notes

- All lists are freshly sorted snapshots: fine for codegen passes, but do not mutate them expecting the schema to change.
