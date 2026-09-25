# .github/release.yml

## What this is
This configures GitHub's auto-generated release notes: which PRs appear and under which headings. PRs labeled `ignore-for-release` or authored by the Smartling translation bot are excluded entirely. Everything else lands under "Features and bug fixes" unless labeled `notes` (internal-build-only changes), `build-setup`, or `dependencies`, which each get their own section.

## How it fits
When the release captain drafts a GitHub pre-release (a checklist step in `scripts/release-pr.md`), GitHub reads this file to build the changelog from merged PR titles — which is why the PR template insists on Conventional Commits titles. The `ignore-for-release` label the template and `open-release-pr.sh` reference only has power because this file honors it. Translation-sync PRs from Smartling are silenced here so they never pollute user-facing notes.

## Key pieces
- **`ignore-for-release` + Smartling author exclusions** — exist to keep internal refactors, flag-hidden work, and automated string imports out of the public changelog.
- **Catch-all `'*'` category with per-label exclusions** — exists so an unlabeled PR still shows up (under Features) instead of silently vanishing, while labeled maintenance work is sorted into its own bucket.

## Junior notes
- Labels drive this file: applying `dependencies` to your Renovate-style PR moves it to the dependency section automatically.
- If your PR should not appear in release notes at all, add the `ignore-for-release` label and say why, per the PR template checklist.
