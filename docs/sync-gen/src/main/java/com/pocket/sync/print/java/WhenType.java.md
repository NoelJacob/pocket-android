# sync-gen/src/main/java/com/pocket/sync/print/java/WhenType.java

## What this is

The single branching point for field-type decisions in generators: instead of scattered if/switch/when chains over FieldType kinds, all code asks this class (is/value/enumm/thing/list/map/variety/interface_/collection/open matchers plus Handler/Match/Handlers/ReturnHandler/ReturnHandlers plumbing). Centralizing means a new type kind surfaces every decision site at once (as compile errors or explicit fallbacks) instead of hiding in one forgotten switch. At ~458 lines, it is all matcher, no mystery.

## How it fits

Used by every generator that varies emission by field kind; adding a FieldType kind starts with extending these matchers.

## Key pieces

- `kind matchers (value/enumm/thing/list/map/variety/interface_/collection/open)` — the per-kind branches generators select among
- `Handler/Match/Handlers/ReturnHandler(s)` — the callback plumbing carrying branch logic with or without return values

## Junior notes

- Never branch on FieldType kinds with raw conditionals in a generator: route through here so new kinds cannot slip past silently.
