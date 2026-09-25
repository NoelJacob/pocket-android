# buildSrc/src/main/kotlin/Plugins.kt

## What this is

This is the versioned build catalog: the single place dependency versions, SDK levels, and plugin declarations live, so bumping a library means editing one file. Think of it as the repo's bill of materials for the build.

## How it fits

Every module's `build.gradle.kts` and the convention plugins reference these constants instead of hardcoding versions. Deps groups libraries by stack (AndroidX/Compose, OkHttp, Hilt, test libs); Configs holds SDK/compile settings; Plugins declares the plugin aliases.

## Key pieces

- `PluginDependenciesSpec` (fun, line 9) — Define plugins here for easy use in other modules
- `PluginDependenciesSpec` (fun, line 12) — entry point other code calls; see callers for context.
- `PluginDependenciesSpec` (fun, line 15) — entry point other code calls; see callers for context.
- `PluginDependenciesSpec` (fun, line 18) — entry point other code calls; see callers for context.
- `PluginDependenciesSpec` (fun, line 21) — entry point other code calls; see callers for context.
- `PluginDependenciesSpec` (fun, line 24) — entry point other code calls; see callers for context.
- `PluginDependenciesSpec` (fun, line 27) — entry point other code calls; see callers for context.
- `PluginDependenciesSpec` (fun, line 30) — entry point other code calls; see callers for context.
- `PluginDependenciesSpec` (fun, line 33) — entry point other code calls; see callers for context.
- `PluginDependenciesSpec` (fun, line 36) — entry point other code calls; see callers for context.

## Junior notes

- Uses Hilt/Dagger dependency injection (`@Inject` constructor parameters provided automatically).
- Uses AndroidX platform APIs.
- BuildSrc code runs at Gradle configuration time: a mistake here breaks every module's build, so change it carefully and sync after editing.
