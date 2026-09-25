# sync-parser/src/commonMain/kotlin/com/pocket/sync/util/FigmentUtils.kt

## What this is

Overflow helpers for schema consumers: small focused utilities useful to codegen but too specific to belong on Definition or Figments, where they would clutter the core API. Keeping them here preserves the core types as clean query surfaces while still sharing the logic across generators instead of duplicating it per generator.

## How it fits

sync-gen generators call these for one-off structural questions during emission. If a helper grows broad enough for general use, promote it onto Figments; while narrow, it stays here.

## Key pieces

- `narrow structural helpers` — single-purpose queries over definitions kept out of the core API

## Junior notes

- Check here before adding a new generator-local helper: a shared, tested version may already exist.
