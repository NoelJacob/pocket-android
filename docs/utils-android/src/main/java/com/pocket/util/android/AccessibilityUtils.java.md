# utils-android/src/main/java/com/pocket/util/android/AccessibilityUtils.java

## What this is

This is a shared utility used across modules: a small, dependency-free helper that one feature needed and others reuse. It owns no app state and starts no work on its own.

## How it fits

Any module depending on `utils` (pure JVM) or `utils-android` (needs Android APIs) imports it directly. It is a leaf in the dependency graph: it must never depend back on feature code.

## Key pieces

- `AccessibilityUtils` (class, line 16) — core type of this file; callers reference it by name.
- `updateAccessibilityState` (fun, line 39) — Updates the accessibility tools state of the parent view tree, based on the new state of the BottomSheetBehavior.
- `updateImportantForAccessibility` (fun, line 57) — This method is adapted from {@link BottomSheetBehavior}'s internal updateImportantForAccessibility method, which loops through all of the Views in the Bottom Sh

## Junior notes

- Uses AndroidX platform APIs.
