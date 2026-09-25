# sync-pocket/sync-pocket-class-generator.jar

## What this is

This is a compiled Java archive (~6244 KB) used at build time: the sync-pocket class generator that produces model/transport code from the GraphQL schemas. Binaries are checked in here so the build does not need to fetch or rebuild the generator.

## How it fits

The `SyncPocketGen` / `SyncGen` buildSrc tasks invoke this jar during compilation of the sync-pocket module (see `sync-pocket/build.gradle.kts` registering the codegen task and `sync-pocket-usage.txt` for the generated API guide). Its output lands in the generated sourceset and is compiled like hand-written code. Provenance: repo-vendored build tool; never decompile or edit it, update it by replacing the file.
