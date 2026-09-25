# Pocket/src/main/java/com/pocket/util/java/package-info.java
## What this is
The package doc for `com.pocket.util.java`: general-purpose, reusable helpers with no Pocket-specific or Android-specific ties. It points readers to the parent `com.pocket.util` docs for the broader util map.
Think of this package as the "plain Java toolbox" (strings, numbers, ranges, callbacks, locks) versus Android-coupled helpers elsewhere.

## How it fits
Every class documented in this batch under `util/java` (from `BytesUtil` and `Range` to `UrlFinder` and `LocaleUtils`) lives under this contract. Screens and features reach for these leaf helpers rather than reimplementing conversions, null guards, or callbacks.

## Key pieces
- Package-level javadoc only; no code or symbols. WHY it exists: states the reuse boundary so Pocket- or Android-coupled logic lands elsewhere.
