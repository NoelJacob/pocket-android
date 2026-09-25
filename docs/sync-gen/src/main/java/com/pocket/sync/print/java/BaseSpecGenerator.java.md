# sync-gen/src/main/java/com/pocket/sync/print/java/BaseSpecGenerator.java

## What this is

Emits the BaseSpec: a nearly-ready Spec implementation with everything derivable from the schema filled in, leaving only the truly hand-written action effects and derive methods abstract for the concrete subclass. classname() names it. This is the class API owners actually extend (SyncTestsSpec extends SyncTestsBaseSpec), so schema updates flow to them as new abstract methods rather than silent behavior changes.

## How it fits

Runs inside Generator.generate(); the emitted base is the parent of every hand-written spec. Pocket's real spec follows the same pattern.

## Key pieces

- `classname` — derives the emitted BaseSpec class name
- `abstract action/derive surface` — the deliberately-unimplemented methods each regeneration may extend

## Junior notes

- A regeneration that adds abstract methods breaks spec subclasses on purpose: the compiler is telling you new product decisions are needed.
