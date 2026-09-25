# .github/PULL_REQUEST_TEMPLATE.md

## What this is
This is the default body every new pull request starts with: a summary prompt, a references section, and setup/review checklists. Most checklist items sit inside HTML comments as opt-in reminders (feature flags, the `ignore-for-release` label, GraphQL usage-file cleanup, Unleash flag housekeeping), so authors uncomment only what applies. The always-visible items require a Conventional Commits title (which drives automated release notes), self-review, basic QA, and code-review approval.

## How it fits
GitHub inserts this template into the PR composer; `.spr.yml` additionally tells `git spr` (a stacked-diff tool some teammates use) where to splice its generated content inside it. The `ignore-for-release` label referenced here is honored by `.github/release.yml` to keep internal churn out of the public changelog. Reviewers check the PR against this list before approving, so it is the quality gate for everything merging to `main`/`beta`.

## Key pieces
- **Conventional Commits title requirement** — exists because release notes are generated from PR titles; a non-conforming title breaks the changelog automation.
- **Commented-out conditional items (feature flag, GraphQL usage cleanup, post-merge Unleash/analytics tasks)** — exist as a menu of common obligations without forcing irrelevant checkboxes onto every PR.
- **`git spr` footer block** — exists to mark the splice region for stacked-diff users; the template warns against deleting it unless the team agrees nobody uses `spr` anymore.

## Junior notes
- Conventional Commits means titles like `feat(home): add save button` — type, optional scope, then description; the linked Mozilla wiki page defines the allowed types.
- Checking the "GraphQL usage file" box means verifying the sync usage file contains only still-valid definitions after a schema change.
