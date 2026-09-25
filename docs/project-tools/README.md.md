# project-tools/README.md

## What this is
This is the one-line charter for the `project-tools` Gradle module: a home for small developer utilities like bulk vector-import scripts. The real documentation lives in the code itself (`Tools.kt`, `ProjectTools.java`) and in `updateToolJars.sh` at the repo root. Think of this module as the repo's toolbox drawer rather than a shipped product.

## How it fits
The module compiles into two committed jars (`tools.jar` and `legacyTools.jar`) that CI and release scripts execute — for example, Bitrise runs `updateBuildVersionForRelease` out of `tools.jar` on every release branch. Developers add new commands as picocli subcommands of `Tools`, then run `updateToolJars.sh` to rebuild the checked-in jars so CI picks them up.
