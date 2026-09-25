# pocket-ui/build.gradle.kts

## What this is

This is the Gradle build script for the `pocket-ui` module, the shared design-system library (themed views, fonts, typography, dimensions). It applies the shared `pocketAndroidLib` convention plugin plus `kotlinKapt` (annotation processing, needed for databinding) and `kotlinCompose` (Jetpack Compose support). It declares the module's namespace (`com.pocket.ui`), turns on viewBinding, Compose, and databinding (XML layouts bound to ViewModel fields), and lists its dependencies.

## How it fits

Every app/feature module that shows Pocket-styled UI depends on `pocket-ui` and gets whatever this script exposes. `implementation(projects.utilsAndroid)` pulls in shared utilities for internal use only. The `api(...)` lines (Lottie animations, ConstraintLayout, ViewPager2, Facebook Shimmer) are re-exported, so callers can use those classes without declaring them again. The rest (`implementation`) covers Compose (BOM plus runtime, UI, Material3, tooling/preview), SwipeRefreshLayout, Google FlexBox, and an image-support library for the views inside this module.

## Key pieces

- `plugins { pocketAndroidLib() / kotlinKapt() / kotlinCompose() }` — WHY: reuses the repo's standard Android-library setup, enables annotation processing for databinding, and sets up Compose compilation instead of hand-rolling that config.
- `android { namespace, viewBinding, compose, dataBinding }` — WHY: `namespace` is the Android package identity; the three feature flags exist because this module ships both old-style Views (viewBinding/databinding XML layouts) and new-style Compose UI side by side.
- `kotlinOptions { freeCompilerArgs += "-opt-in=...ExperimentalTextApi" }` — WHY: opts into the experimental Compose text API used by `PocketTypography`, so the compiler allows those calls.
- `composeCompiler { includeSourceInformation = true }` — WHY: embeds source locations in composition output, which makes Compose tooling metrics and debugging traces useful.
- `api` vs `implementation` dependencies — WHY: `api` leaks the dependency to consumers (callers can reference Lottie/ConstraintLayout types directly); `implementation` keeps it private to this module (Compose, FlexBox, etc. are implementation details of the widgets).

## Junior notes

- `api` vs `implementation` matters for build speed and coupling: prefer `implementation` unless callers genuinely need the type in their own code.
- `platform(libs.androidx.compose.bom)` is a bill-of-materials: it pins all Compose libraries to one tested version so individual `implementation` lines don't need versions.
