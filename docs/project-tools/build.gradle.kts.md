# project-tools/build.gradle.kts

## What this is
This is the Gradle build file for the `project-tools` developer-utilities module. It applies the JVM Kotlin plugins, pulls in picocli (a command-line argument parsing library) plus its annotation processor and Apache Commons IO, and registers two jar-packaging tasks. The module produces no app code — only command-line tools run from CI or a terminal.

## How it fits
Running `./gradlew project-tools:toolJar` (or `legacyToolJar`) compiles `Tools.kt` (or `ProjectTools.java`) and its dependencies into the committed `tools.jar` (or `legacyTools.jar`) at the module root. `updateToolJars.sh` automates exactly those two invocations plus the copy step. Bitrise and release scripts then execute the jars with `java -jar`, so this file is the link between the Kotlin/Java sources and the runnable artifacts CI depends on.

## Key pieces
- **`kotlinJvm()` + `kotlinKapt()` plugins** — provide Kotlin compilation and annotation processing (picocli generates help/completion code at compile time); no Android plugin because these are plain JVM tools.
- **`Deps.Picocli.picocli` / `codeGen` / `Deps.Commons.IO.commonsIo` dependencies** — picocli turns annotated classes into CLI commands; Commons IO gives the legacy tools file helpers.
- **`registerJarTask("legacyToolJar", mainClass = "...ProjectTools", ...)`** — packages the old Java entry point; kept so existing CI steps (`-printVersionName`, `-printVersionCode`) keep working.
- **`registerJarTask("toolJar", mainClass = "com.pocket.tools.ToolsKt", ...)`** — packages the new Kotlin entry point; new commands (like `updateBuildVersionForRelease`) go here.

## Junior notes
- `registerJarTask` is a custom helper from `buildSrc`, not a built-in Gradle function — it creates a fat jar (all dependencies bundled) with the given main class.
- `ToolsKt` is the JVM class name Kotlin generates for the `main()` function in `Tools.kt` (top-level functions get a `Kt` suffix).
