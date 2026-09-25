# sync/src/main/java/com/pocket/sync/package-info.java

## What this is

This is the front door and setup guide for the whole Sync engine: a small framework where immutable snapshots called Things (for example a saved item: its URL, title, favorite flag) are kept in a local in-memory registry called the Space, and user intents called Actions (save, archive, favorite) are applied to that Space first and sent to the server second. You never hand-write the Thing/Action classes: you describe your API in GraphQL schema files and the sync-gen module generates them, plus a Spec object that wires everything together.

## How it fits

Every other file under sync/ implements one piece named here, so read this before touching engine code. App authors follow its six setup steps (define schema, run codegen, subclass the generated BaseSpec, pick a Source such as AppSource) and end up with a Source object that powers screens; Pocket's own implementation lives in the sdk modules and the generated test spec in sync-pocket-android shows a complete working example. End to end, saving a URL walks through this file like this: the app builds a generated save Action (inputs: the URL) and hands it to the source, which applies it to the local Space first via the Spec so the UI updates instantly (this optimistic local apply is why saves feel immediate), then queues the Action for the remote transport; the server reply is parsed back into Things that are imprinted into the Space through the Resolver, and every subscriber watching those Things receives a Changes update and re-renders.

## Key pieces

- `setup steps (schema, codegen, BaseSpec, Source)` — the checklist that turns a GraphQL schema into a working app data layer
- `updating your API` — why schema edits are cheap: change the schema, re-run codegen, implement only the new action/derive methods
- `layers guidance` — tells you whether a change belongs in a Thing/Action, the parser, codegen, or this engine library

## Junior notes

- Jargon map used across all sync docs: a Thing is server-state data, an Action is a user-intent mutation, the Space is local memory, a Source is anything the app reads/writes through, a Spec is the codegen'd API definition, a Remote is the server transport.
- This engine is Pocket-agnostic on purpose; Pocket specifics (V3 endpoints, GraphQL operations) live in the sdk modules, so keep generic engine changes here and API changes there.
