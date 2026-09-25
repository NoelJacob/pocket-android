# sync/src/main/java/com/pocket/sync/source/RemoteStyle.java

## What this is

Names one server naming convention for syncing remotely: which aliases and envelope shapes apply when talking to a given endpoint family (V3 versus GraphQL client API, for example). Generated code stamps each operation with its style, and JsonConfig carries the active style into every parse/serialize call so field mapping stays consistent per endpoint.

## How it fits

RemoteStyleGenerator creates the concrete enum from schema endpoint declarations, and GraphQlSource/JsonConfig consume it at runtime. Adding a new endpoint family means adding a style here via the schema, not hand-editing call sites.

## Key pieces

- `style values` — the per-endpoint conventions selecting alias sets and envelope handling

## Junior notes

- Wrong-style parsing is silent field loss (nulls, not crashes), so suspect the style before suspecting the payload.
