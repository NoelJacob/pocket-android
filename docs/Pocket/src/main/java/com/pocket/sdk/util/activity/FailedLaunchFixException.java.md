# Pocket/src/main/java/com/pocket/sdk/util/activity/FailedLaunchFixException.java
## What this is
A tiny marker exception that means "the app was launched with a bad Intent and the launch-flag workaround failed". It carries only a message string describing which flags or categories were wrong. It exists so the one place that applies the launch fix can report exactly why it gave up.
## How it fits
Thrown and caught inside `AuthenticationActivity`, which tries a workaround for bad launch flags when the app is opened. The catch block disables the workaround (`ALLOW_LAUNCH_FIX` preference set to false) and reports the error through the app's error reporter. Nothing else in the app throws or catches it.
## Key pieces
- `FailedLaunchFixException(String message)` — the only constructor; the message records the offending flags/categories (or an empty Intent) so crash reports explain what launch failed.
## Junior notes
- A checked `Exception` (not `RuntimeException`) forces the caller to handle it with try/catch, which is how the disable-and-report fallback is guaranteed to run.
- `@SuppressWarnings("serial")` quiets the missing-serial-id warning; exceptions are almost never serialized here, so no id is needed.
