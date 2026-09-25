# scripts/release-pr.md

## What this is
This is the release runbook template: the checklist body stamped into every release PR by `scripts/open-release-pr.sh`. It walks the release captain through preparing the GitHub pre-release and changelog, waiting for the CI "release prep" commit, checking the merged manifest for surprise permissions, promoting builds through Google Play tracks (internal → beta → production), and merging `beta` back into `prod` and `main`. The `{release-version}` placeholder is filled in per release.

## How it fits
The automation chain starts when a `release-*` branch is pushed: `on-git-reference-created.yml` runs `open-release-pr.sh`, which substitutes the version into this file and creates the PR against `beta`. Humans then execute the checklist inside that PR — Play Console promotions, `#pocket-releases` announcements, Confluence changelog — and the Bitrise `onReleaseBranchUpdated` workflow handles the build/sign/upload half. Nothing here runs as code; it is process documentation that ships with the PR.

## Key pieces
- **Prepare-the-release section (pre-release + generated changelog + summary)** — exists so release notes are drafted and reviewed before anything ships, with an explicit "don't publish yet" gate.
- **Merged-manifest permission check** — exists because dependency upgrades can silently add Android permissions; this forces a human to confirm each one is production-necessary and to notify support when they change.
- **Play promotion chain (internal → beta → production with rollout percentages)** — exists to stage risk: beta soaks first, production follows, with managed-publishing guidance for timing the rollout.
- **Merge-back steps (`beta` → `prod`, `beta` → `main`, never squash)** — exists so release commits stay linear and traceable across the long-lived branches.

## Junior notes
- "Do not squash" matters here: squash-merging would rewrite the release commit hashes that the changelog and Play builds reference.
- `{release-version}` appears in the GitHub release-link template; the script replaces it, so editors should never hardcode a version in this file.
