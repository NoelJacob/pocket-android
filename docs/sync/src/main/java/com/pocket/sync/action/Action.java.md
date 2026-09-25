# sync/src/main/java/com/pocket/sync/action/Action.java

## What this is

The core mutation type of the engine: an Action carries the inputs and values of one user intent (save this URL, archive that item) while the effect of that intent lives separately in Spec.apply. Action instances are immutable (unchangeable after creation, so they can be safely shared across threads and retried), and each one knows its name, its timestamp, how urgently it should be sent to the server, and how to parse the server's reply about it.

## How it fits

App code creates Actions through generated builder classes and hands them to a Source such as AppSource, which applies them locally to the Space and forwards them to the Remote transport. End to end, saving a URL walks through this file like this: the app builds a generated save Action (inputs: the URL) and hands it to the source, which applies it to the local Space first via the Spec so the UI updates instantly (this optimistic local apply is why saves feel immediate), then queues the Action for the remote transport; the server reply is parsed back into Things that are imprinted into the Space through the Resolver, and every subscriber watching those Things receives a Changes update and re-renders.

## Key pieces

- `action()` — the action's name, matching the mutation declared in the GraphQL schema
- `time()` — when the intent happened, used for ordering and conflict decisions
- `priority()` — a RemotePriority hint telling the source whether to send this to the server immediately or let it wait
- `builder()` — returns a pre-filled builder for this Action's type so callers can tweak inputs into a new immutable copy
- `resolved()` — parser for the server's reply about this action, or null when the action returns nothing

## Junior notes

- Things are state, Actions are intents: if you are adding a read-model field it is a Thing change, if you are adding something the user does it is an Action.
- Never mutate an Action in place; builder() plus build() is the only way to derive a variant.
