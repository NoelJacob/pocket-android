# Architecture.md

## What this is
This is the living status document for the stripped local-build variant of the app: what was removed from the original Pocket codebase, what was faked locally, and how to build and verify the result. It reads as three layers — the `just-app` analytics strip, the `pocket-local-server` backend plus emulator end-to-end loop, and the single-`fdroid` flavor with vendor SDKs removed. Treat it as the authoritative "where are we" note for this branch, not general Pocket architecture.

## How it fits
Start here when onboarding to this branch: it tells you the exact build command (`./gradlew clean Pocket:assembleDevelopDebug`), where the APK lands and what application ID to expect, which files carry the `// ponytail:` local-build markers, and how the app is repointed at `http://10.0.2.2:8080` in debug builds. The Phase 2 and F-Droid sections name the proof bar (fake login → save Wikipedia URL → paged list → parsed ArticleView render) that the screenshot/e2e loop checks.

## Key pieces
- **Analytics-strip inventory (removed module, tracker DI, view interfaces, impression chain)** — exists so nobody re-adds telemetry plumbing thinking it is missing; each bullet names the deleted surface and its replacement (e.g. `SavesTab` recreated in `com.pocket.app.list`).
- **Kept/faked-locally list (empty `getSecret()`, system-font fallback, forced premium)** — exists to document exactly which runtime behaviors are deliberate local simplifications versus production truth.
- **Phase 2 backend mapping (`v3/guid`, `send`, `get`, `fetch`, `getAfterLogin`, `/parser`, `/graphql`)** — exists to bind each server route to its schema source, strongest first (`*.graphqls` files, generated models, `V3Source` docs, unit tests).
- **F-Droid flavor notes (single flavor, deleted team/play signing, removed vendor SDKs, APK path)** — exists as the build-and-verify contract: what was deleted, what the APK is called, and what clean-tree green means here.

## Junior notes
- `// ponytail:` markers flag intentional shortcuts (forced premium, simplified chooser) — they are tracked simplifications, not leftover TODOs.
- "Debug-gated" means the local-server URLs apply only to debug builds; release code paths still reference production, so always verify against the flavor you built.
