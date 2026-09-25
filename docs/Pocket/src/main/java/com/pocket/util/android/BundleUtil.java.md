# Pocket/src/main/java/com/pocket/util/android/BundleUtil.java

## What this is
A one-method safety wrapper for reading a custom `Parcelable` (Android's fast object serialization for Bundles/Intents) out of a `Bundle`. It solves the `ClassNotFoundException` crash that happens when Android unparcels with the wrong class loader, e.g. after process death restore or multi-module class loading. For example, calling `BundleUtil.getParcelable(bundle, KEY, MyData.class)` temporarily swaps in your class's loader so the lookup succeeds, then restores the original.

## How it fits
It lives in the Android-specific util package as a defensive helper for any screen or fragment that passes custom `Parcelable` objects through arguments or saved state. A grep of `Pocket/src` shows no current callers, so it is dormant infrastructure rather than active plumbing — but the pattern it guards (parcelables in Bundles) is used across bottom sheets via sibling helpers like `putEnum`/`enumArg` in `NavigationExtensions`-adjacent files.

## Key pieces
- `getParcelable(bundle, key, clazz)`: null-safe read that sets the bundle's class loader to `clazz`'s loader, reads, and restores the previous loader in all cases after the read. WHY it exists: the save/restore dance is easy to get wrong inline, and forgetting to restore the loader corrupts later reads.

## Junior notes
- Always pass the exact class (`MyData.class`), not a base class: the loader swapped in is that class's loader.
- Null bundle or missing key returns null rather than throwing — callers must still null-check the result.
- On modern Android, `Bundle.getParcelable(key, clazz)` (API 33+) does this natively; this helper predates that and remains for older API levels.
