# sync-gen/src/main/java/com/pocket/sync/print/java/Generator.java

## What this is

The orchestrator for a whole generation run: create one, call generate(), and it fans out to every per-concept emitter to produce the complete set of Things, Actions, specs, modellers, and helpers. Its Generation Design javadoc explains the architecture (why many small emitters coordinated centrally rather than one giant printer). Config controls what and where; Figments supplies the model.

## How it fits

Entry points (ExamplesGenerator, SyncTestsGenerator, AndroidClassGenerator via CommandLineGeneration) all drive this; individual *Generator classes never run standalone in production. Read this before adding a new emitter: it shows where to hook in.

## Key pieces

- `generate()` — the full fan-out producing every emitted file for one API
- `Generation Design docs` — the architecture rationale for the many-emitter design

## Junior notes

- New emission capability means a new emitter plus a hook here: resist bolting unrelated output onto an existing generator.
