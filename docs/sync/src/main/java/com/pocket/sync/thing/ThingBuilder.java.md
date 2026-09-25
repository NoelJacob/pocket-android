# sync/src/main/java/com/pocket/sync/thing/ThingBuilder.java

## What this is

The builder interface every generated Thing builder implements: set fields one by one, then build() the immutable Thing. It mirrors ActionBuilder on the state half of the domain, and generated builders add one typed setter per schema field plus with()-style copy helpers for deriving modified copies. MutableThing also builds on these internally.

## How it fits

App and test code assemble Things through generated builders (see ThingMock and the FlatTest fixtures); the Space only ever stores the built immutables. buildThing in SyncExtensions wraps these for Kotlin call sites.

## Key pieces

- `build()` — freezes staged fields into the immutable Thing the Space can store and share

## Junior notes

- Builders are staging objects, not storage: build promptly and drop the builder rather than caching half-filled ones.
