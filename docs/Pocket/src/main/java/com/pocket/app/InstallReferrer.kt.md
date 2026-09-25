# Pocket/src/main/java/com/pocket/app/InstallReferrer.kt
## What this is
This source file no longer exists in the repo: it was listed in the handoff chunk but there is no `InstallReferrer.kt` under `Pocket/src/main/java/com/pocket/app/`. It previously read the Google Play install referrer (which ad/campaign link the install came from), stored it, and reported it to Pocket servers for attribution.
## How it fits
Nothing references this file now. Its former collaborators were the `Pocket` sync instance and analytics-context types for tagging the referrer event. If install attribution is ever rebuilt, the replacement would run once early in app start (near `App.onCreate` / first-run logic in `Versioning`) and feed analytics, not UI.
## Key pieces
- No symbols remain; the old doc mentioned referrer setup callbacks and a stored-referrer getter, all removed with the file.
## Junior notes
- Play's install-referrer API requires the referrer client library and an async connection; do not reimplement with a plain intent extra, which is spoofable and unreliable.
- If stale references to `InstallReferrer` remain in docs or code, delete them rather than recreating the file.
