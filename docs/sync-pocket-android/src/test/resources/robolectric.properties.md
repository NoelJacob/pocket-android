# sync-pocket-android/src/test/resources/robolectric.properties

## What this is

A test-runner property file pinning the Robolectric SDK level (Robolectric is the framework that runs Android-dependent unit tests on the JVM by shadowing Android APIs). sdk=28 fixes the emulated API level so shadows behave identically on every machine.

## How it fits

Read by Robolectric for every unit test in this module; bump only when a test needs newer shadow behavior, and expect snapshot churn if you do.

## Entries

- Content: sdk=28
