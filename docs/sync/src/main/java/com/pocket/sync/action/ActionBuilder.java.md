# sync/src/main/java/com/pocket/sync/action/ActionBuilder.java

## What this is

The builder interface every generated Action builder implements: set the action's input values one by one, then call build() to freeze them into an immutable Action. Builders exist because Actions themselves are immutable, so there has to be a mutable staging object during construction. Generated builders add one typed setter per schema input.

## How it fits

UI and repository code use the generated builder (for example a save action's builder with a url setter) to assemble an Action and then pass the built Action to a Source. The engine only ever sees the finished immutable Action, never the builder.

## Key pieces

- `build()` — freezes the staged inputs into the immutable Action the engine will apply and sync

## Junior notes

- Builders are single-use staging objects: build what you need, then drop the builder rather than reusing it across calls.
