# sync-pocket-android/src/test/java/com/pocket/sdk/api/PocketSpecTest.java

## What this is

Pins the PocketSpec derive behavior for currentlyReading: the derived field tracking what the user is actively reading. Derives are computed-from-other-fields values, so this proves the derive wiring recomputes correctly as underlying state changes, complementing ItemActionsSpecTest's action coverage with derive coverage.

## How it fits

Runs against the PocketSpec with scripted state; failures point at derive rules or Reactions gathering, not transports.

## Key pieces

- `currentlyReading` — recomputation of the active-reading derived field as inputs change

## Junior notes

- Derive tests fail in two places: wrong rule (spec) or missed trigger (reactions); check the Diff coverage when debugging.
