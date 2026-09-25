# Pocket/proguard-rules.txt

## What this is

The ProGuard/R8 shrink rules applied to `unsignedRelease` builds (wired up in `Pocket/build.gradle.kts`). They tell the optimizer which classes must survive name-shrinking and which third-party warnings to ignore, plus they preserve line numbers so release stack traces stay debuggable.

## How it fits

Release builds run `proguard-android.txt` plus this file; without these keeps, reflection-based libraries (retrofit annotations, Jackson, Parcelable `CREATOR`, kotlinx.serialization companions) would break at runtime after obfuscation. Entry inventory: line-number attributes; `NoObfuscation` keeps for `com.pocket.util.android`; `dontwarn` suppressions for OkHttp/okio, TwitterKit/okhttp/retrofit/rx, Jackson, JSoup, Twitter4J, Apache HTTP, Play Services, Guava/checkerframework; keeps for retrofit-annotated methods, `SafeParcelable`, `@KeepName`, `Parcelable.CREATOR`, Adjust SDK, install-referrer, `onClickUpdate(View)` handlers, and the kotlinx.serialization `Companion`/`serializer()`/`INSTANCE` rules.
