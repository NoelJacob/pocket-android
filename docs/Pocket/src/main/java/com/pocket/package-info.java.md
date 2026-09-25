# Pocket/src/main/java/com/pocket/package-info.java

## What this is

The package-level Javadoc for `com.pocket`, the root of all Pocket-written code. It is a three-bullet map: `com.pocket.sdk` is the (aspirational) shared SDK, `com.pocket.app` is the official app, and `com.pocket.util` is Pocket-built but app-agnostic tooling. It openly admits `sdk` vs `sdk2` is mid-refactor and not yet clean.

## How it fits

Javadoc tooling attaches this comment to the `com.pocket` package, so it shows up as the overview page for anyone browsing generated docs. It is the companion to `Pocket/README.md`: the README is the onboarding tour, this file is the in-code signpost; both point feature work at `com.pocket.app` and shared logic at `com.pocket.sdk`/`sdk2`.

## Key pieces

- **`com.pocket.sdk` link (with the honesty caveat)** — why it exists: to mark the goal that reusable Pocket logic converges here, while warning that `sdk2` currently holds incremental refactor steps toward a cleaner SDK.
- **`com.pocket.app` link** — why it exists: to name the one package that owns Activities and user-facing behavior, so new screens start there.
- **`com.pocket.util` link** — why it exists: to give non-Pocket-specific helpers (Android utilities, tools) a home that other projects could reuse without dragging Pocket logic along.

## Junior notes

- **`package-info.java` only holds the comment; the `package com.pocket;` line is the payload.** Deleting the file loses the Javadoc overview but changes no behavior.
- **Treat the `sdk` vs `sdk2` TODO as live guidance.** If you are unsure where shared code goes, ask before adding a third home for it.
