# scripts/pre-commit.sh

## What this is
This is the git pre-commit hook body: a tiny script that keeps the Gradle version catalog sorted whenever it is staged. If `gradle/libs.versions.toml` is among the staged files, it runs the `sort-version-catalog.main.kts` Kotlin script on it and re-stages the result. If the catalog is untouched, the hook does nothing and the commit proceeds normally.

## How it fits
It is wired in by `settings.gradle.kts` through the `gradle-pre-commit-git-hooks` plugin, which installs this file as the repo's actual `.git/hooks/pre-commit` on every Gradle sync. That means every developer's commit automatically normalizes catalog ordering, so version-bump diffs stay clean and Renovate PRs never fight over entry order. The catalog itself lives at `gradle/libs.versions.toml`.

## Key pieces
- **`command -v kotlinc` guard** — exists to fail with an install hint (`brew install kotlin`) instead of a cryptic "file not found" when the Kotlin runner is missing.
- **`git diff --cached --name-only | grep -q` check** — exists so the hook only pays the cost of sorting (and only touches the index) when the catalog is actually part of this commit.
- **`git add --` re-stage** — exists because sorting rewrites the working-tree file after it was staged; without re-adding, the commit would capture the unsorted version.

## Junior notes
- A pre-commit hook is a script git runs before creating a commit; a non-zero exit blocks the commit.
- The `.main.kts` suffix means the sorter is a Kotlin script run directly by the `kotlin` runner, not a compiled program.
