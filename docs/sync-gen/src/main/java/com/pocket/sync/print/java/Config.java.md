# sync-gen/src/main/java/com/pocket/sync/print/java/Config.java

## What this is

What-and-where configuration for a generation run: output locations, file kinds to emit (thing/enumm/action helpers), value modeling choices (modelValue/timeValue), GraphQL support toggle (enableGraphQl), and the compat/usage file (compatFile). The nested Builder assembles it fluently; PocketConfig/ExamplesConfig/SyncTestsConfig specialize it per API. Every generator reads this instead of taking scattered parameters.

## How it fits

Built by each entry point's ConfigCreator (via CommandLineGeneration) and threaded through Generator and all emitters. Changing output layout or per-type modeling starts here.

## Key pieces

- `thing/enumm/action/value helpers` — per-kind emission path and naming configuration
- `modelValue/timeValue` — how schema values and timestamps map to Java types
- `enableGraphQl/compatFile` — operation-support emission toggle plus the backwards-compat ledger path
- `Builder` — fluent assembly of one complete generation configuration

## Junior notes

- One Config per API keeps Pocket, examples, and tests from entangling: never share instances across APIs.
