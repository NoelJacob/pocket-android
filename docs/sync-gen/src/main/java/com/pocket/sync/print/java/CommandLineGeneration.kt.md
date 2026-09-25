# sync-gen/src/main/java/com/pocket/sync/print/java/CommandLineGeneration.kt

## What this is

The standard main() for codegen jars: parses CLI arguments, builds the Config via a ConfigCreator, and runs generation. Each codegen entry point (ExamplesGenerator, SyncTestsGenerator, AndroidClassGenerator) funnels through main/create here instead of reimplementing argument handling, so all generator jars share flags and behavior. It is the reason codegen runs uniformly from Gradle tasks.

## How it fits

Entry point for generator jars invoked by build logic (see registerSyncTestGenTask usage in sync-pocket-android); Config selects what and where to emit.

## Key pieces

- `main/create` — CLI parsing plus Config-driven generation shared by every generator jar
- `ConfigCreator` — the hook each entry point supplies to build its own Config

## Junior notes

- Add a new generator jar by writing a Config plus a thin main delegating here, not by forking argument parsing.
