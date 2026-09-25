# sync-gen/examples/examples-usage.txt

## What this is

A backwards-compatibility ledger (usage file): one line per definition and aspect previously used in builds, with stable numeric ids. The ids are part of the binary persistence format, so this history is what keeps old disk data restoring after schema evolution. Maintained by UsageFile, consulted by UsageModeCalculator.

## How it fits

Read during codegen to decide NORMAL/COMPAT/SKIP fates and to assign ids; committed back with updates. Checked in so every build shares the same history. Never delete lines: retired history is load-bearing.

## Entries

- Tracked kinds: - x5, action x14, enum x6, thing x55, value x7
- 87 ledger lines
