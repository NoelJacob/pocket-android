# sync/src/main/java/com/pocket/sync/source/InMemorySource.java

## What this is

The simplest real Source: keeps everything in memory with no server and no disk. It implements the client sync, subscribe, remember/forget, and initialize operations against a plain in-memory Space, which makes it perfect for unit tests, previews, and transient sessions where persistence would only get in the way.

## How it fits

Tests use this wherever they need a working Source without network or SQLite (many engine tests stand one up in a few lines), and throwaway tooling can use it for scratch state. Anything needing a server uses AppSource plus a Remote instead.

## Key pieces

- `sync/subscribe/remember/forget/contains/initialize` — the working client-source surface backed by a memory-only Space
- `spec` — exposes the Spec so generic engine code can ask what this source is capable of

## Junior notes

- State evaporates when the process dies; if a test needs store-and-restore coverage it must use a persisted Space, not this.
