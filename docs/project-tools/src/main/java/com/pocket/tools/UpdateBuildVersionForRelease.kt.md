# project-tools/src/main/java/com/pocket/tools/UpdateBuildVersionForRelease.kt

## What this is
This command sets the app version for a release branch: it takes a branch name like `release-8.33.0`, extracts major/minor/patch, and rewrites the `val versionMajor/Minor/Patch/Build` lines in `Pocket/build.gradle.kts`. If the branch version matches what is already in the file, it only bumps the build number by one; otherwise it writes the new triple and resets the build number to zero. It is a picocli `Callable` (a command object whose `call()` does the work) registered under the name `updateBuildVersionForRelease`.

## How it fits
Bitrise's `onReleaseBranchUpdated` workflow runs this from the committed `tools.jar` (`java -jar project-tools/tools.jar updateBuildVersionForRelease $BITRISE_GIT_BRANCH`) before building and signing, so the produced APK's `versionName`/`versionCode` always matches the branch. It replaces the older `ProjectTools.java` setters for the release flow because it understands the branch-name convention and the reset-build-on-new-version rule in one place.

## Key pieces
- **`branchName` `@Parameters(BRANCH_NAME)`** — exists to take the branch as a positional argument (e.g. `release-8.33.0`) so CI passes `$BITRISE_GIT_BRANCH` verbatim with no parsing upstream.
- **Same-version → bump build / different-version → reset build branching** — exists to encode the release policy directly: re-running on the same branch yields a fresh build number, while cutting a new version starts counting from zero.
- **Line-index find-and-replace on the gradle file** — exists to change exactly the four version lines and nothing else, preserving comments and formatting; `getVersionNumber()` parses the current values with the same prefix matching.
- **`max` comments in rewritten lines (`Max value of 200`, `Max of three digits`)** — exist to remind the next editor of each field's Play Store / version-code packing ceiling at the point of change.

## Junior notes
- The file must be run from the repo root (`File("./Pocket/build.gradle.kts")` is a relative path), which is why CI checks out the repo first.
- `versionBuild` feeds the last segment of `versionName` and part of `versionCode` — resetting it to zero on a new major/minor/patch keeps codes monotonic per release line.
