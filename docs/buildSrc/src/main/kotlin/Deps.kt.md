# buildSrc/src/main/kotlin/Deps.kt

## What this is

This is the versioned build catalog: the single place dependency versions, SDK levels, and plugin declarations live, so bumping a library means editing one file. Think of it as the repo's bill of materials for the build.

## How it fits

Every module's `build.gradle.kts` and the convention plugins reference these constants instead of hardcoding versions. Deps groups libraries by stack (AndroidX/Compose, OkHttp, Hilt, test libs); Configs holds SDK/compile settings; Plugins declares the plugin aliases.

## Key pieces

- `Deps` (object, line 6) — Allows referencing dependencies in gradle files in a type-safe and discoverable way.
- `AndroidX` (object, line 7) — core type of this file; callers reference it by name.
- `ViewPager2` (object, line 8) — core type of this file; callers reference it by name.
- `ConstraintLayout` (object, line 12) — core type of this file; callers reference it by name.
- `Test` (object, line 16) — core type of this file; callers reference it by name.
- `Lifecycle` (object, line 20) — core type of this file; callers reference it by name.
- `SwipeRefreshLayout` (object, line 26) — core type of this file; callers reference it by name.
- `Android` (object, line 31) — core type of this file; callers reference it by name.

## Junior notes

- Uses RxJava reactive streams (`Observable`/`Single`).
- Uses JUnit/Mockito/MockWebServer tests.
- Uses AndroidX platform APIs.
