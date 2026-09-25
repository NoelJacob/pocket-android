# Pocket/src/main/java/com/pocket/sdk/build/AlphaBuild.kt

## What this is
Small setup hook for internal/team ("alpha", also called nightly) builds. Its only job is to configure and start Microsoft AppCenter Distribute, which delivers over-the-air updates to testers. It does nothing on production builds. The `Nightly` interface beside it is just an alias so the class is findable under both names.

## How it fits
Created by Hilt DI as a singleton; something in the app startup path calls `setup()`. `setup()` checks `AppMode.isForInternalCompanyOnly` and, if true, configures AppCenter with the build's AppCenter id and starts the Distribute module. It has no callers downstream beyond the AppCenter SDK itself.

## Key pieces
- `setup()` — guards on internal-only mode, lazily configures AppCenter once (`isConfigured` check), and starts `Distribute`. Exists so testers get update prompts while production never initializes the SDK.
- `Nightly` — empty proxy interface for `AlphaBuild`. Exists purely for discoverability: historically "alpha", now commonly "nightly".

## Junior notes
- `AppMode` is the build-flavor switch (dev vs team-alpha vs production); gating on `isForInternalCompanyOnly` is the standard way to keep internal tooling out of release builds.
- The comment warns alpha logic is scattered; when adding nightly-only behavior, check whether related code already lives elsewhere before creating a new home.
