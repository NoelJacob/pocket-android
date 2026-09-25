# scripts/sort-version-catalog.main.kts

## What this is
This is a tiny Kotlin script that sorts `gradle/libs.versions.toml` section by section so version-catalog diffs stay minimal and reviewable. It splits the file on blank lines (each chunk is one TOML section like `[versions]` or `[libraries]`), sorts the lines inside each chunk alphabetically, and writes the file back. Section order and comments attached to lines are preserved; only within-section ordering changes.

## How it fits
It is invoked by `scripts/pre-commit.sh` (installed as the git pre-commit hook via `settings.gradle.kts`) whenever the catalog is staged, with the catalog path as `args[0]`. That hook then re-stages the sorted result, so every commit lands with a normalized catalog whether the developer remembered to sort or not. Renovate's automated dependency PRs benefit the same way — no ordering churn.

## Key pieces
- **Split-on-blank-line, sort-within-chunk** — exists so `[versions]`, `[libraries]`, and `[plugins]` blocks are each alphabetized without mixing entries across sections.
- **`versionCatalog.writeText(sorted)` overwrite** — exists to normalize the file in place; there is no backup because git itself is the undo.

## Junior notes
- The `.main.kts` extension plus the `#!/usr/bin/env kotlin` shebang lets this run as a script (`script path args`) without a compile step — but it does require the `kotlin` runner installed.
- Sorting is plain lexicographic (uppercase before lowercase), which is why all catalog keys are lowercase by convention.
