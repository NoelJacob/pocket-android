# .github/workflows/on-git-reference-created.yml

## What this is
This workflow fires whenever any git reference (branch or tag) is created and acts only on branches starting with `release-`. Its single job opens the release PR by running `scripts/open-release-pr.sh` with the new branch name. It is the automation that turns "pushed a release branch" into "release runbook PR exists".

## How it fits
Pushing `release-8.33.0` triggers this workflow, which checks out the repo and runs the script with `GITHUB_TOKEN` (an auto-provided token that lets the workflow create PRs) as `GH_TOKEN`. The script creates a PR from the release branch into `beta` using `scripts/release-pr.md` as the body. From there the human release process in that checklist takes over, and Bitrise's `onReleaseBranchUpdated` workflow handles building and signing in parallel.

## Key pieces
- **`on: create` trigger with `startsWith(github.ref_name, 'release-')` gate** — exists so ordinary feature branches cost zero CI minutes while release branches always get their PR.
- **`scripts/open-release-pr.sh "${{ github.ref_name }}"` step** — the workflow's entire payload: delegates branch parsing and PR creation to the tested script rather than embedding shell in YAML.
- **`GH_TOKEN: ${{ secrets.GITHUB_TOKEN }}`** — exists to authenticate the `gh` CLI as the workflow itself, scoped to this repo.

## Junior notes
- `ref_name` is the short branch/tag name (`release-8.33.0`), not the full ref — the prefix check depends on that.
- This runs on branch creation only; subsequent pushes to the release branch do not re-trigger it (Bitrise watches those instead).
