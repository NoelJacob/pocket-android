# README.md

## What this is
This is the repo's front door: it opens with the July 2025 shutdown notice (Pocket is discontinued; the repo is historical reference only), then maps the multi-module project for newcomers. It lists each Gradle module's role — the `Pocket` app, `pocket-ui` components, `utils`/`utils-android` shared helpers, the `sync-*` engine family with its parser and code generator, and `project-tools` utilities — with a dependency diagram showing how they connect. It closes with pointers to code style, Renovate-managed dependencies, sync-engine docs, and Gradle/fonts topics.

## How it fits
Read this before touching any module: the module list tells you where a change belongs (app screen → `Pocket`, reusable view → `pocket-ui`, sync behavior → `sync`/`sync-pocket`), and the mermaid diagram shows which way dependencies flow (the app consumes the engine, never the reverse). The sync-engine section routes you to the deeper docs (`sync-gen`/`sync-parser` READMEs, `package-info.java` files, `V3Source`/`ClientApiSource`/`MutableSpace` class docs, schema files) once you know which layer you are in.

## Key pieces
- **Shutdown notice + Mozilla announcement link** — exists to set expectations before anything else: no active development, no bug fixes or security patches.
- **Module catalog (`Pocket`, `pocket-ui`, `utils`, `sync-parser`, `sync-gen`, `sync`, `sync-android`, `sync-pocket`, `project-tools`)** — exists so a newcomer can place any file in the architecture within a minute.
- **Mermaid dependency diagram** — exists to make the layering visual: `Pocket` → engine → utils, codegen feeding the engine, UI components shared sideways.
- **Sync-engine reading list (READMEs, `package-info.java`, key classes, schemas, Figment history)** — exists because engine knowledge is scattered; this ordered list is the guided path in.
- **Coding-conventions, Renovate, Gradle-config, and Fonts pointers** — exist to route the four most common follow-up questions (style, dependency updates, build setup, typefaces) without duplicating those docs.

## Junior notes
- The mermaid code block renders as a diagram on GitHub but reads as plain text locally — follow the `--->` arrows to trace dependencies.
- `figments` (mentioned via `sync-parser`) are the in-memory model of the GraphQL schema that `sync-gen` turns into Java code; you will meet them as soon as you touch the sync layer.
