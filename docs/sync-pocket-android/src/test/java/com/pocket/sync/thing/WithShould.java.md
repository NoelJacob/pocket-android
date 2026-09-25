# sync-pocket-android/src/test/java/com/pocket/sync/thing/WithShould.java

## What this is

Copy-semantics proofs for every with() variant codegen emits: replacing identifiables, replacements nested in non-identifiables, in lists and maps of each, plus interface fields in single, list, mixed-field, and mixed-list positions. With-methods are the immutable-update path (copy with one field changed), so each structural position needs its own proof that identity and siblings survive.

## How it fits

Guards ThingGenerator's with() emission across the full structural matrix; failures mean generated copies corrupt graphs.

## Key pieces

- `replace_identifiable / within_non_identifiable` — direct and embedded replacement preserving surroundings
- `replace_within_list/map_of_identifiables/non_identifiables` — collection-position replacements
- `replace_interface_in_*` — interface-typed fields in every container position
- `replace_within_non_identifiable / replace_within_list_of_identifiables / replace_within_list_of_non_identifiables / replace_within_map_of_identifiables / replace_within_map_of_non_identifiables / replace_interface_in_field / replace_interface_in_list / replace_interface_in_mixed_field` — further cases in this file covering adjacent behavior of the same contract

## Junior notes

- Immutable-update bugs alias instead of copy: the tell is an unrelated Thing changing when only one field was with-ed.
