# sync-pocket-android/src/main/java/com/pocket/sdk/AndroidPocket.java

## What this is

Android factory for Pocket SDK instances: Config/Builder assemble a Pocket object with Android-sensible defaults (thread pools, publishers, storage, device info) so app code calls pocket() or builds with authenticationExtras instead of wiring engine pieces by hand. Factories and builders exist because a working Pocket needs a dozen coordinated parts (spec, space, remote, resolver, pools, publisher) that no screen should assemble. This is the front door the app's DI graph calls.

## How it fits

Used at app startup/DI setup to create the shared Pocket instance; LoggingPocket wraps the result for debug builds, and feature code then uses the Pocket like any client source. AndroidDeviceInfo supplies the device half of the config.

## Key pieces

- `pocket()/Builder` — one-call and customized construction of a ready Android Pocket instance
- `authenticationExtras` — auth-scoped extras threading login state into the built instance
- `Config` — the Android-defaulted assembly of spec, space, remote, pools, and publisher

## Junior notes

- Build the Pocket once and share it: separate instances mean separate Spaces that disagree about the same items.
- Android defaults here assume app process lifetime; tests construct lighter instances directly instead.
