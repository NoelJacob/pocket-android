# gradlew

## What this is
This is the POSIX Gradle wrapper launcher: a self-contained shell script that lets anyone build with `./gradlew` using only a JVM installed. It resolves its own location (following symlinks), sets up default JVM options, locates `java`, and execs the committed `gradle/wrapper/gradle-wrapper.jar`, which downloads the pinned Gradle 8.14.3 on first run. It handles Cygwin, MinGW, macOS, and Linux path quirks so the same command works everywhere.

## How it fits
Every documented build flows through here: `./gradlew clean Pocket:assembleFroidDebug`, `./gradlew :Pocket:lint`, `:Pocket:licensee`, `:sync-pocket:test`, and the `project-tools:*Jar` packaging tasks. CI calls the same script (`GRADLEW_PATH: ./gradlew` on Bitrise; direct `./gradlew` in GitHub workflows), so local `./gradlew Pocket:assembleDevelopDebug` reproduces CI behavior exactly. The version it runs is pinned by `gradle/wrapper/gradle-wrapper.properties`, not by this script.

## Key pieces
- **Symlink resolution + `APP_HOME` detection** — exists so the script finds the wrapper jar no matter where it is invoked from or linked to.
- **`DEFAULT_JVM_OPTS` / `JAVA_OPTS` / `GRADLE_OPTS` layering** — exists to separate wrapper defaults from your environment overrides without editing the script.
- **OS-specific tweaks (darwin file-descriptor limits, cygwin path conversion, NonStop)** — exist to normalize the environment before Java starts; you never touch these.
- **Final `exec "$JAVACMD" … -jar gradle-wrapper.jar "$@"`** — the actual handoff: your arguments become the wrapper's (and then Gradle's) arguments verbatim.

## Junior notes
- Always use `./gradlew`, never a system `gradle` — the system one is whatever version happens to be installed and will resolve dependencies differently.
- If the wrapper fails with "JAVA_HOME is not set", point `JAVA_HOME` at a JDK 21 install matching `.java-version`.
