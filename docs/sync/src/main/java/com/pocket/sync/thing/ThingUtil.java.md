# sync/src/main/java/com/pocket/sync/thing/ThingUtil.java

## What this is

Static field-comparison plumbing the generated equals/hashCode methods delegate to: merge for combining maps, mapEquals/listEquals/fieldEquals for type-aware comparisons, mapHashCode/collectionHashCode/fieldHashCode for matching hashes, and castList/castSet for safe collection conversions. Centralizing this keeps hundreds of generated classes consistent: one fix here repairs equality everywhere instead of requiring regeneration. (Its TODO Documentation is honest: the code is self-describing enough that nobody has written the prose yet.)

## How it fits

Generated Thing/Action equals and hashCode call these; EqualityTest pins the resulting semantics (identity versus state, nested and collection cases). Feature code never calls these directly.

## Key pieces

- `mapEquals/listEquals/fieldEquals` — null-safe, type-aware field comparisons for generated equals
- `mapHashCode/collectionHashCode/fieldHashCode` — matching hash contributions keeping the equals/hashCode contract
- `merge/castList/castSet` — combining and converting field collections during comparisons and copies

## Junior notes

- Equals/hashCode bugs here corrupt Space lookups silently (lost updates, duplicate rows): treat changes as engine-critical and run EqualityTest.
