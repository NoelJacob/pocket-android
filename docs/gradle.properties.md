# gradle.properties

## What this is
This file holds three Gradle-wide flags: AndroidX enabled, a 6 GB Gradle heap, and the legacy (non-full) R8 mode. Each line is a global setting every module build inherits. It is the place for "how the build behaves" knobs, as opposed to the catalog (which versions dependencies) or module files (which declare them).

## How it fits
`android.useAndroidX=true` routes all support-library imports through AndroidX (the current library family) — without it, legacy `android.support` resolution breaks the compile. `org.gradle.jvmargs=-Xmx6144M` gives the Gradle daemon room for the multi-module graph (mirrored by Bitrise's `_JAVA_OPTIONS`). `android.enableR8.fullMode=false` keeps the older, more lenient code shrinker after the AGP 8 upgrade; flipping it is tracked as post-upgrade work in the linked developer docs.

## Key pieces
- **`android.useAndroidX=true`** — opts the whole project into AndroidX artifact resolution; required by every `androidx.*` dependency in the catalog.
- **`org.gradle.jvmargs=-Xmx6144M`** — sizes the build JVM heap for parallel module compilation; reduce it only if your machine cannot spare the RAM.
- **`android.enableR8.fullMode=false`** — restores pre-AGP-8 shrinking defaults to avoid newly aggressive dead-code removal while the upgrade settles.

## Junior notes
- `gradle.properties` at the root applies to all modules; a second one in `~/.gradle/` applies to your machine only — local overrides belong there, never committed.
- R8 is the shrinker/obfuscator that strips unused code in release builds; "full mode" removes more aggressively but can break reflection-based code.
