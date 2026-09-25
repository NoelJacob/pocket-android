# sync-gen/src/main/java/com/pocket/sync/print/java/package-info.java

## What this is

Declares this package as the codegen core: given schema files, create a Config and run Generator to produce the Things, Actions, and supporting classes the sync engine needs (the com.pocket.sync library is the runtime they plug into). It references the figment/sync spec docs and sketches the assume-schema-then-configure-then-generate flow that every API follows.

## How it fits

The starting javadoc for anyone extending codegen; concrete Configs and entry points live in the pocket/tests/examples subpackages.

## Key pieces

- `schema-to-code flow` — the documented path from .graphqls files to a working generated API

## Junior notes

- Extend the engine by adding emitters here; add an API by writing a Config in a subpackage, not by forking this package.
