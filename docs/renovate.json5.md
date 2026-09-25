# renovate.json5

## What this is
This configures Renovate (the automated dependency-update bot) for the repo. It inherits Pocket-wide defaults from `Pocket/renovate-config`, ignores the legacy `buildSrc/Deps.kt` file while the version-catalog migration finishes, skips the dashboard-approval gate so PRs open freely, auto-merges minor/patch/pin/digest updates, shortens Gradle commit topics, and adds a custom regex manager that keeps `.java-version` current with new JDK majors.

## How it fits
Renovate watches `gradle/libs.versions.toml` (and the wrapper properties) and opens version-bump PRs that flow through the normal gates: `pre-commit.sh` sorting, `on-pull-request.yml` lint/license checks, and human review for major bumps only. The `ignorePaths` entry for `Deps.kt` prevents duplicate PRs against the old mechanism during the migration. Group-by-group modernization work (AndroidX, OkHttp, Hilt, tests) consumes these PRs deliberately rather than all at once.

## Key pieces
- **`extends: local>Pocket/renovate-config`** — inherits shared policy (schedules, reviewers, range strategy) so this file only records repo-specific deviations.
- **`ignorePaths: buildSrc/.../Deps.kt`** — exists to silence the bot on the legacy definitions while they still exist but should no longer move.
- **`packageRules` automerge for minor/patch/pin/digest** — exists so safe updates merge themselves after green CI, leaving human attention for majors.
- **`commitMessageTopic: '{{depName}}'` for gradle managers** — trims redundant wording from already-long Gradle update titles.
- **`customManagers` regex on `.java-version`** — teaches Renovate to treat that one-line file as a `java` dependency tracking JDK majors, extracting just the leading digits.

## Junior notes
- JSON5 (unlike strict JSON) allows comments and trailing commas — that is why this file is `.json5` and full of `//` explanations.
- If a Renovate PR looks wrong (bad range, wrong grouping), fix it by editing this config, not by hand-editing the bot's branch.
