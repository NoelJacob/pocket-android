# Pocket/src/test/java/com/pocket/app/build/VersionUtilTest.java
## What this is
Round-trip tests for `VersionUtil`, which packs a four-part version (major, minor, patch, build) into a single integer version code and unpacks it back into a dotted name. It pins exact encodings such as 7.0.38.384 mapping to 70038384.
## How it fits
Guards production `com.pocket.app.VersionUtil`, used at build/release time to derive Android `versionCode` / `versionName`. Pure JVM JUnit test with no Android or coroutine dependencies.
## Key pieces
- `toVersionCode()` — asserts several tuples encode to expected integers, including the two-arg overload `toVersionCode(4, 0)`; WHY: field widths must pack without collision.
- `toVersionName()` — asserts integers decode back to dotted strings like `107.0.3.384`; WHY: verifies the decode path is the exact inverse.
## Junior notes
- Version codes are integers with fixed-width segments, so large majors (107) still fit; adding a segment would break stored codes.
- This is a symmetric codec test: any change to one direction must keep the other direction's expectations in sync.
