# settings.gradle.kts

## What this is
This is the module registry and project bootstrap: it names the root project `pocket-android`, includes all eleven modules (`Pocket`, `pocket-ui`, `project-tools`, `utils`, `utils-android`, `sync`, `sync-gen`, `sync-android`, `sync-pocket`, `sync-pocket-android`, `sync-parser`), installs `scripts/pre-commit.sh` as the git pre-commit hook via the pre-commit plugin, and enables typesafe project accessors (compile-checked references between modules). Any new module must be added to the `include()` list here or Gradle will not see it.

## How it fits
This file runs before any module build file: it decides the project graph every `./gradlew` invocation operates on. The `gitHooks { preCommit { from(file("scripts/pre-commit.sh")) } }` block (from the `gradle-pre-commit-git-hooks` plugin) is what puts catalog sorting into every developer's commit flow on sync. `TYPESAFE_PROJECT_ACCESSORS` turns `project(":sync")` strings into generated `projects.sync` accessors so a renamed module breaks the compile instead of silently resolving wrong.

## Key pieces
- **`rootProject.name = "pocket-android"`** — the identity used in settings paths, IDE titles, and composite-build references.
- **Eleven `include()` lines** — the module inventory: app (`Pocket`), UI kit (`pocket-ui`), shared utils, the five-piece sync engine plus its parser/generator, and the dev-tools module.
- **`gitHooks.preCommit.from(scripts/pre-commit.sh)` + `createHooks(overwriteExisting = true)`** — installs (and refreshes) the hook on every sync so catalog sorting cannot be skipped by an old checkout.
- **`enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")`** — opts into generated type-safe module references across all build files.

## Junior notes
- Adding a module is two steps: `include(":name")` here plus a `name/build.gradle.kts` — forgetting the first gives "project not found", forgetting the second gives an empty-project error.
- The pre-commit plugin version (`2.1.0`) is pinned in the `plugins` block at the top; bump it like any other plugin, then re-sync to reinstall hooks.
