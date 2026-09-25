# Pocket/src/main/java/com/pocket/sdk/package-info.java
## What this is
Package overview comment for `com.pocket.sdk`: the shared Pocket-specific layer with core APIs like syncing, offline downloading, item/database access, plus reusable views and base Android components.
## How it fits
This file holds no code, only the `package` statement and its doc comment; it tells newcomers that anything under `sdk/` (image loading, sync, offline cache, preferences, notifications) is cross-feature infrastructure that activities and services build on, rather than one screen's code.
## Key pieces
- Package javadoc — the two-sentence scope note quoted above; the authoritative "what belongs in sdk/" definition.
## Junior notes
- `package-info.java` is the standard Java place for package-level docs; tooling (javadoc, IDE hovers) picks it up, so keep the scope description current when new subsystems move in.
