# sync-gen/src/main/java/com/pocket/sync/print/java/JavaDescriptionPrinter.java

## What this is

Turns schema Description nodes into Javadoc: the single print() entry renders descriptions as proper /** */ comments on emitted classes and members. Schema authors document once in GraphQL; every generated class carries the prose to the IDE. Small file, large documentation ROI.

## How it fits

Called by all emitters when adorning generated types; schema doc comments flow through here. If generated docs look mangled, this one method is the suspect.

## Key pieces

- `print` — description-to-Javadoc rendering shared by all emitters

## Junior notes

- Write schema descriptions as user-facing API docs: they become the IDE tooltips for every generated class.
