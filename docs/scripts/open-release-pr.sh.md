# scripts/open-release-pr.sh

## What this is
This script opens the release pull request automatically when a `release-*` branch is created. It takes the branch name as its only argument, derives the version from it (everything after `release-`), and stamps that version into the `scripts/release-pr.md` checklist template. It then calls the GitHub CLI (`gh`) to create a PR targeting the `beta` branch with the `ignore-for-release` label.

## How it fits
Nobody runs this by hand: the `on-git-reference-created.yml` GitHub workflow invokes it with the new branch name and a `GITHUB_TOKEN` whenever a branch matching `release-*` appears. The produced PR body is the release runbook (Play promotion steps, changelog steps, merge-back steps), so this script is the entry point to the whole release process described in `release-pr.md`.

## Key pieces
- **`RELEASE_BRANCH=$1` plus the missing-argument guard** — exists to fail loudly instead of opening a PR with an empty title when the workflow forgets the branch name.
- **`RELEASE_VERSION=${RELEASE_BRANCH#release-}`** — shell prefix-stripping (a built-in string operation, no subprocess) that turns `release-8.33.0` into `8.33.0` for the PR title.
- **`sed … | gh pr create --body-file -`** — fills the `{release-version}` placeholder in the template and pipes the result as the PR body; `--head` is the release branch, `--base` is `beta`.

## Junior notes
- `set -e` at the top means the script aborts on the first failing command rather than opening a half-formed PR.
- `gh` is the GitHub command-line client; it authenticates here via the `GH_TOKEN` env var the workflow provides.
