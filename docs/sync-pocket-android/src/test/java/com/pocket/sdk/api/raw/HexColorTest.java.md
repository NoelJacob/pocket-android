# sync-pocket-android/src/test/java/com/pocket/sdk/api/raw/HexColorTest.java

## What this is

Validation unit tests for the HexColor raw value: throwsIfEmpty/throwsIfWrongSize/throwsIfNotHex prove malformed inputs are rejected, validates proves well-formed hex passes. Raw values are thin wrappers around wire strings with strict parsing, and rejecting early keeps bad color data from spreading into themes and UI.

## How it fits

Pure unit tests with no engine involvement; run on any change to the HexColor scalar modeling.

## Key pieces

- `throwsIfEmpty / throwsIfWrongSize / throwsIfNotHex` — the three rejection paths for malformed color strings
- `validates` — acceptance of well-formed hex

## Junior notes

- Strict-by-default scalar parsing is intentional: lenient parsing would push color failures into distant UI crashes.
