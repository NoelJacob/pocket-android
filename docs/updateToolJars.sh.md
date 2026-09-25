# updateToolJars.sh

## What this is
This script rebuilds the two committed tool jars after you edit anything in `project-tools/`. It runs both Gradle jar tasks (`project-tools:legacyToolJar` and `project-tools:toolJar`), deletes the stale jars at the module root, and copies the fresh outputs from `project-tools/build/libs/` over them. CI consumes the jars directly, so without this step your source edits never reach the agents.

## How it fits
The loop is: edit `ProjectTools.java` or anything under `com.pocket.tools` → run this from the repo root → commit the regenerated `legacyTools.jar`/`tools.jar` alongside your source change. Bitrise's release workflow (`java -jar project-tools/tools.jar updateBuildVersionForRelease …`) and the version-printer steps then execute your new code. Reviewers should expect jar diffs (binary, unreadable) paired with the source diff on every project-tools PR.

## Key pieces
- **Two `./gradlew project-tools:*Jar` invocations** — recompile each entry point (`ProjectTools` legacy, `ToolsKt` modern) into fresh fat jars; running both keeps them in lockstep.
- **`rm` + `cp` from `build/libs/`** — replaces the committed artifacts atomically-ish: delete stale, copy fresh, so no half-written jar survives a failed build.

## Junior notes
- Committing build outputs is unusual but deliberate here: CI agents run the jars without a compile step, trading repo bloat for faster, hermetic release steps.
- Never hand-copy jars or rename them — the scripts reference these exact paths (`project-tools/tools.jar`, `project-tools/legacyTools.jar`).
