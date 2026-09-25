# .github/actions/setup-environment/action.yml

## What this is
This is the shared composite action (a reusable bundle of workflow steps) that sets up the build environment for every GitHub CI job. It optionally installs a JDK and/or Gradle based on boolean inputs, both defaulting to off so each job declares exactly what it needs. It is the single place where toolchain versions are pinned for CI.

## How it fits
Both jobs in `on-pull-request.yml` (Android Lint and license validation) call this with `gradle: true`, which in turn pulls in the JDK — so a version bump here affects every PR check at once. The JDK version comes from the `.java-version` file at the repo root (currently 21) using the Temurin distribution (chosen because GitHub's runners cache it, making jobs faster); Gradle setup uses the official `gradle/actions/setup-gradle` action with dependency caching.

## Key pieces
- **`jdk` / `gradle` inputs (default `'false'`)** — exist so jobs opt into only what they need; today everything needs Gradle, but a future docs-only job could request neither.
- **`if: inputs.jdk == 'true' || inputs.gradle == 'true'` on the JDK step** — exists because Gradle cannot run without Java; requesting Gradle implicitly requests the JDK.
- **Pinned third-party action SHAs (`setup-java`, `setup-gradle`)** — exist to freeze CI against upstream rewrites; Dependabot/Renovate bumps these hashes deliberately, never implicitly.

## Junior notes
- A composite action is like a function for workflows: `uses: ./.github/actions/setup-environment` calls into this repo-local path rather than an external marketplace action.
- `java-version-file: '.java-version'` means CI reads the same JDK version developers use locally — changing that one file changes both.
