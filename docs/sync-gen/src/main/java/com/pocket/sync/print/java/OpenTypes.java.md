# sync-gen/src/main/java/com/pocket/sync/print/java/OpenTypes.java

## What this is

Emission support for open types (interfaces and varieties): setupForField/setupForSyncable/setupForInterface/setupForVariety generate the discriminator plumbing, creator switches, and collection variants each open field needs, with uniqueVarieties deduping union shapes and creatorReference linking to the right creator. Open fields are the hardest emission problem (many possible concrete types per field), so this centralizes it instead of spreading conditionals through every generator.

## How it fits

Used by ThingGenerator/SyncableGenerator whenever a field's type is open; runtime dispatch lands in OpenParser. OpenUsages/UnknownVarietyExample outputs show the results.

## Key pieces

- `setupForField/setupForSyncable` — per-field and per-operation open-type plumbing emission
- `setupForInterface/setupForVariety` — contract-type versus union-type emission paths
- `uniqueVarieties/creatorReference` — union dedup plus creator linkage

## Junior notes

- Open-type emission and OpenParser runtime dispatch must agree on discriminators: change the _type convention in both or neither.
