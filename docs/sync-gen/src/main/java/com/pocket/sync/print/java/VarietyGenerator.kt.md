# sync-gen/src/main/java/com/pocket/sync/print/java/VarietyGenerator.kt

## What this is

Emits the per-union plumbing for variety (closed-union) types: the create entry builds the variety support for one union declaration (member set, discriminator handling, creator dispatch). Varieties are GraphQL-union-shaped: one value, a fixed set of possible Thing types. OpenTypes assists with shared open-type mechanics; OpenParser performs the runtime dispatch.

## How it fits

Runs per variety inside generation; VarietyExample/UnknownVarietyExample show emitted output including the forward-compatible unknown-member path.

## Key pieces

- `create` — per-union support emission: members, discrimination, and creators

## Junior notes

- Closed today, extended tomorrow: always verify the unknown-member path for a new union before shipping.
