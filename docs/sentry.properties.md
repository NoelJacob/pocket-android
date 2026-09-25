# sentry.properties

## What this is
This three-line file configures Sentry's build-time mapping-upload integration: the project (`android`) and org (`pocket`) slugs plus a comment recording that the API key arrives via environment, not version control. Sentry is the crash-reporting SDK — its Gradle plugin uses these values to upload obfuscation maps so production stack traces de-obfuscate in the dashboard.

## How it fits
The `plugin-sentry` / `sentry` dependencies in the version catalog apply the upload step to release builds; this file tells the plugin where to send the mappings. The key itself is owned by the "Android mapping upload" internal integration and injected as an env var in CI, which is why no secret appears here. Stripping Sentry (a vendor-SDK removal step) means deleting this file alongside the plugin, dependency, and `SentryManager` wiring.

## Key pieces
- **`defaults.project=android` / `defaults.org=pocket`** — the upload destination; mismatching these sends mappings where the dashboard never looks.
- **API-key-via-environment comment** — the security contract in one line: the key is provisioned by CI and never committed.

## Junior notes
- Debug builds skip mapping upload — if you are chasing a local crash, `adb logcat` (logcat = Android's system log stream) is the tool, not the Sentry dashboard.
- Deleting Sentry support must also remove the OkHttp interceptor/listener the plugin wires, or release builds fail with missing-class errors.
