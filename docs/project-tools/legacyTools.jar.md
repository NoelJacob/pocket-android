# project-tools/legacyTools.jar

## What this is
This is the committed, runnable build of the legacy Java developer tools (`ProjectTools.java`) — a fat jar bundling the code plus its dependencies. It is checked in so CI agents can run version commands without compiling anything. It is a build artifact, not source: never edit it directly.

## How it fits
Bitrise and developers invoke it as `java -jar project-tools/legacyTools.jar -printVersionName Pocket/build.gradle.kts` (or `-printVersionCode`, `-setAppVersionPart`, `-incrementAppVersionPart`) to read or bump the app's version fields. It is regenerated from `src/main/java/com/pocket/legacy/ProjectTools.java` by running `updateToolJars.sh` (which calls `./gradlew project-tools:legacyToolJar` and copies the output here).
