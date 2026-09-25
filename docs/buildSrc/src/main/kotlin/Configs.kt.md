# buildSrc/src/main/kotlin/Configs.kt

## What this is

This is the versioned build catalog: the single place dependency versions, SDK levels, and plugin declarations live, so bumping a library means editing one file. Think of it as the repo's bill of materials for the build.

## How it fits

Every module's `build.gradle.kts` and the convention plugins reference these constants instead of hardcoding versions. Deps groups libraries by stack (AndroidX/Compose, OkHttp, Hilt, test libs); Configs holds SDK/compile settings; Plugins declares the plugin aliases.

## Key pieces

- `AndroidConfigs` (object, line 9) — core type of this file; callers reference it by name.
- `KotlinConfigs` (object, line 15) — core type of this file; callers reference it by name.
- `JavaConfigs` (object, line 20) — core type of this file; callers reference it by name.
- `BaseExtension` (fun, line 29) — Set android configurations for a [BaseExtension].
- `HasConfigurableKotlinCompilerOptions` (fun, line 41) — entry point other code calls; see callers for context.

## Junior notes

- BuildSrc code runs at Gradle configuration time: a mistake here breaks every module's build, so change it carefully and sync after editing.
