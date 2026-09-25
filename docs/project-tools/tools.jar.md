# project-tools/tools.jar

## What this is
This is the committed, runnable build of the modern Kotlin developer tools (`Tools.kt` plus subcommands) — a fat jar bundling the code with picocli and its dependencies. It is checked in so CI can run release commands without a compile step. It is a build artifact, not source: never edit it directly.

## How it fits
Bitrise's release workflow invokes it as `java -jar project-tools/tools.jar updateBuildVersionForRelease $BITRISE_GIT_BRANCH` to stamp the branch version into `Pocket/build.gradle.kts` before signing. It is regenerated from `src/main/java/com/pocket/tools/` by running `updateToolJars.sh` (which calls `./gradlew project-tools:toolJar` and copies the output here).
