# scripts/set-version-name-build-number.sh

## What this is
This script stamps the CI build number into the app's version name by replacing the `$versionBuild` placeholder in `Pocket/build.gradle.kts` with the number passed as its first argument. It uses `sed -i` (in-place stream edit) with a `.bkp` backup suffix so the same command works on both macOS and Linux `sed`. The backup file is a portability artifact, not something to commit.

## How it fits
Bitrise's `publishAlphaToAppCenter` workflow calls this with `$BITRISE_BUILD_NUMBER` before building the alpha, so every CI-produced APK carries a unique, traceable build suffix (e.g. `8.33.0.1234`). Local developer builds skip it and keep whatever `$versionBuild` value is checked in. The release-branch flow instead uses the `updateBuildVersionForRelease` project-tools command, which understands major/minor/patch semantics — this script is the simpler alpha-only path.

## Key pieces
- **`BUILD_NUMBER=$1` with no default** — exists to make a missing argument produce a visibly broken version rather than silently reusing the old number.
- **`sed -i'.bkp' -e "s/$versionBuild/$REPLACEMENT/"`** — the whole operation: finds the literal `$versionBuild` token (a Kotlin string template in the gradle file) and swaps in the CI number; the `.bkp` extension satisfies macOS `sed`, which requires a backup suffix with `-i`.

## Junior notes
- `set -e` aborts on any failure so a bad `sed` never leads to building with a stale version.
- If you run this locally by mistake, restore `Pocket/build.gradle.kts` from git and delete the `.bkp` file it leaves behind.
