# scripts/run-android-lint.sh

## What this is
This is a one-command wrapper that runs Android Lint over the main app module (`./gradlew :Pocket:lint`). Lint is Android's static analyzer — it scans code and resources for bugs, dead resources, accessibility gaps, and API misuse without running the app. The wrapper exists so humans and CI invoke the same canonical command.

## How it fits
The `on-pull-request.yml` GitHub workflow calls this script in its `android-lint` job after checking out the repo, setting up JDK/Gradle, and decrypting secrets. Findings are gated by the repo's `lint.xml` baseline at the root (which disables everything except `UnusedResources` as an error). Developers can run the same script locally before pushing to catch failures early.

## Key pieces
- **`./gradlew :Pocket:lint`** — the whole script: targets only the `:Pocket` app module because that is where UI, resources, and manifest issues concentrate; library modules are out of scope for this gate.

## Junior notes
- `set -e` means any lint failure (non-zero exit) fails the script and therefore the CI job.
- Lint reports land under `Pocket/build/reports/lint-results-*.html` — open that file in a browser to read them comfortably.
