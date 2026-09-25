# sync-parser/src/commonMain/kotlin/com/pocket/sync/usage/UsageFile.kt

## What this is

The backwards-compatibility ledger: a file listing every definition and aspect (field, enum option) previously used in builds, with include/exclude/included/excluded registration, stable numeric id() assignment (generateIdFor), and commit() persistence. Numeric ids become part of the binary persistence format, so reusing an old id for a new field would corrupt restores; this file is what prevents that. A null path runs purely in memory (tests) without touching disk. isUsageTracked selects which definition kinds participate; the enum-aspect quirk in toUsage preserves history for old files.

## How it fits

UsageModeCalculator registers against this during codegen and commits the updated ledger; checked-in usage files (examples-usage.txt, sync-tests-usage.txt) are the lived-in instances. Deleting entries is never safe: excluded history is load-bearing.

## Key pieces

- `include/exclude/included/excluded` — registering and querying which definitions and aspects are live versus retired
- `id/generateIdFor` — stable numeric ids keeping the binary format compatible across versions
- `commit` — persisting the updated ledger back to disk

## Junior notes

- Never hand-edit usage files to remove lines: removed history gets misread as never-used, and ids can be reassigned to new fields.
- In-memory mode (null path) is for tests; production codegen always passes a real path so history accumulates.
