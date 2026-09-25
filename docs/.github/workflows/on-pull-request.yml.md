# .github/workflows/on-pull-request.yml

## What this is
This workflow runs the two automated PR gates: Android Lint and license validation. It triggers on every pull request and on merge-queue batches, cancels superseded runs when new commits arrive, and runs both jobs on Ubuntu runners with pinned action versions. A green result on both jobs is what lets a PR merge.

## How it fits
Each job follows the same prologue — checkout, `setup-environment` with `gradle: true` (JDK + Gradle + caching), then `secrets/decrypt.sh` with the `gpg_key` repository secret — because both Lint and `licensee` need the full decrypted source tree to be accurate. The jobs then diverge: one runs `scripts/run-android-lint.sh` (`:Pocket:lint`), the other `scripts/run-license-checks.sh` (`:Pocket:licensee :sync-gen:licensee`). These are the GitHub-side complements to Bitrise's heavier build-and-test pipelines.

## Key pieces
- **`concurrency: cancel-in-progress: true` keyed by workflow + ref** — exists so pushing a fix cancels the stale run instead of burning runners on code nobody will merge.
- **`android-lint` job** — exists to catch resource, accessibility, and API-misuse issues statically via the `:Pocket:lint` task gated by root `lint.xml`.
- **`licenses` job** — exists to block dependencies with unacceptable licenses via the `licensee` plugin before they reach `main`.
- **`GPG_KEY: ${{ secrets.gpg_key }}` on both decrypt steps** — exists to give each job the team passphrase non-interactively; the value stays redacted in logs.

## Junior notes
- `merge_group` in the trigger means these checks also run when GitHub batches PRs for the merge queue, not just on direct PR pushes.
- Pinned `actions/checkout` SHAs (with `# v4.3.0` comments) freeze the checkout behavior; version bumps are deliberate hash changes, not floating tags.
