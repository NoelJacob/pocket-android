# bitrise.yml

## What this is
This is the Bitrise CI/CD configuration: the workflows that build, sign, and distribute the app outside GitHub. It defines four triggers (main pushes, release-branch pushes, merge-queue pushes, all pull requests) and three main workflows: alpha publishing to AppCenter on `main`, release-branch builds signed and shipped to the Play Store's internal track, and lightweight pre-merge checks (decrypt, unit tests, review build) for PRs and queued merges.

## How it fits
GitHub workflows handle fast PR gates (lint, licenses); Bitrise handles everything needing secrets, signing, and store uploads. The release flow calls the committed project-tools jars (`updateBuildVersionForRelease` from `tools.jar`, version printers from `legacyTools.jar`), runs `secrets/decrypt.sh`, copies the merged manifest for permission review (a `release-pr.md` checklist item), builds the `playUnsignedRelease` variant, signs it via Mozilla's Autograph service, and deploys to the Play internal track before pushing a "release prep" commit back to the branch. Alpha builds instead stamp `$BITRISE_BUILD_NUMBER` via `set-version-name-build-number.sh` and ship the team release to AppCenter.

## Key pieces
- **`trigger_map` (main / release-* / merge-queue / PR routing)** — exists to send each push to the right workflow so release signing never runs on a feature branch.
- **`onReleaseBranchUpdated` (version stamp → decrypt → manifest copy → build → Autograph sign → Play deploy → push prep commit)** — the release pipeline; each step's output feeds the next, ending with the human checklist in the release PR.
- **`publishAlphaToAppCenter` (build-number stamp → decrypt → teamATeamRelease → AppCenter)** — the nightly-style alpha lane distributing test builds to the team group.
- **`runPreMergeChecks` (decrypt → unit tests → `$PR_BUILD_VARIANT` review build)** — the heavyweight PR validation complementing GitHub's lint/license jobs.
- **App-level envs (`MODULE: Pocket`, `PR_BUILD_VARIANT: teamReviewTeamRelease`, Gradle memory flags)** — shared defaults so every workflow builds the same module with the same heap settings.

## Junior notes
- `activate-ssh-key` and `git-clone` steps look boilerplate but are load-bearing: private submodules and pushing the prep commit both need the SSH identity.
- The Autograph `curl` step uploads the unsigned APK and downloads the signed one — signing happens on Mozilla infrastructure, never with a local keystore.
