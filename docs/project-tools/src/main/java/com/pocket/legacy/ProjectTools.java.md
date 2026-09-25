# project-tools/src/main/java/com/pocket/legacy/ProjectTools.java

## What this is
This is the legacy command-line toolbox for reading and bumping the Pocket app's version numbers inside `Pocket/build.gradle.kts`. It supports four commands: print the version name, print the version code, set one version part, or increment one version part. It works by scanning the gradle file's lines for the `versionMajor/Minor/Patch/Build` declarations and rewriting them. New development happens in the Kotlin `Tools.kt` instead; this file stays for backward compatibility with existing CI steps.

## How it fits
CI (Bitrise release flow) and developers run it via the committed `project-tools/legacyTools.jar` as `java -jar … -printVersionName Pocket/build.gradle.kts` and friends — no compile step needed on the agent. After editing this file you must run `updateToolJars.sh` from the repo root to rebuild the jar, otherwise CI keeps running the stale code. The version fields it edits feed the app's `versionName`/`versionCode` and ultimately what users see in Settings and the Play Store.

## Key pieces
- **`main()` command dispatcher (`-setAppVersionPart`, `-incrementAppVersionPart`, `-printVersionCode`, `-printVersionName`)** — exists so one jar serves all four version operations; it parses `args` positionally and prints a stack trace plus non-zero exit on any failure.
- **`VersionPart` enum (MAJOR max 210, MINOR/PATCH max 99, BUILD max 999)** — exists to encode each part's variable-name postfix and ceiling in one place, so validation and name-mapping cannot drift apart.
- **`setAppVersionPart()` / `incrementAppVersionPart()` (String and enum overloads)** — exist to validate the new value against the part's max, then rewrite just that line of the gradle file while leaving everything else byte-identical.
- **`printVersionCode()` / `printVersionName()`** — exist so CI scripts can capture the current version into shell variables for tagging, signing, and upload steps.
- **`getVersionParts()` line scanner** — exists to parse the four `val versionX = N` lines into an int array both printers and mutators share, so there is exactly one parser for the gradle version block.

## Junior notes
- The Android-Studio debug recipe in the class comment (JAR Application configuration pointing at `legacyTools.jar`) is the supported way to step through a command locally.
- A `versionCode` is Android's internal integer build identity (must increase every release); `versionName` is the human string (e.g. `8.33.0.123`) shown to users.
