# project-tools/src/main/java/com/pocket/tools/Tools.kt

## What this is
This is the entry point for the modern project-tools CLI: a thin launcher plus a command registry. The `main()` function hands `args` to picocli (a library that turns annotated Kotlin classes into subcommands with `--help` for free) and exits with picocli's status code. The `Tools` class itself does nothing except list available subcommands — today just `updateBuildVersionForRelease`.

## How it fits
The module builds into the committed `project-tools/tools.jar` (via `./gradlew project-tools:toolJar` or `updateToolJars.sh`), and CI invokes it as `java -jar project-tools/tools.jar updateBuildVersionForRelease release-8.33.0` during the Bitrise release flow. To add a new tool, you write a picocli `@Command` class anywhere in this module and register it in the `subcommands` array here — no other wiring needed.

## Key pieces
- **`main()` + `exitProcess(CommandLine(Tools()).execute(*args))`** — exists to bridge the JVM entry point to picocli's parser and to propagate the command's exit code to the calling shell/CI step.
- **`Tools` holder `@Command` with `subcommands = [UpdateBuildVersionForRelease::class]`** — exists as the single registry of tools; adding a class here is the entire "wiring" for a new command.
- **`mixinStandardHelpOptions = true`** — exists to give every command `--help` and `--version` output without writing any help code.

## Junior notes
- picocli (pronounced "pickle") maps command-line tokens onto annotated fields (`@Parameters`, `@Option`) — reading `UpdateBuildVersionForRelease` next to this file shows the pattern in ten lines.
- The comment block's invitation to put new commands "wherever you please" is deliberate: group by feature, not by framework.
