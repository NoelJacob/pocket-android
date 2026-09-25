# lint.xml

## What this is
This is the repo's Android Lint baseline: it disables every check by default and opts back into exactly one — `UnusedResources` at error severity, with translated string files excluded. In other words, the only thing that fails a lint run today is a truly unused resource in the default locale. It is deliberately minimal, not neglected.

## How it fits
`scripts/run-android-lint.sh` (`./gradlew :Pocket:lint`) evaluates this file on every PR via `on-pull-request.yml`; a stray drawable, layout, or default-locale string breaks the build until removed or referenced. The two `ignore` regexes for `Pocket/.../values-*/strings.xml` and `pocket-ui/.../values-*/strings.xml` exist because the Smartling translation sync opens PRs aligning translations with the base file — flagging those as unused would fight the automation.

## Key pieces
- **`<issue id="all" severity="ignore" />`** — the default-off switch; new checks are adopted by adding explicit entries, never by surprise.
- **`<issue id="UnusedResources" severity="error">`** — the single enforced check: dead resources bloat the APK, so they fail the gate.
- **Translation-file `ignore` regexps** — exist to tolerate temporarily unused strings in non-default locales between the base-file change and Smartling's sync PR.

## Junior notes
- To trial a new lint check locally, add it here with `severity="warning"` first — warnings report without failing CI — then promote to `error` once the codebase is clean.
- Lint reports render at `Pocket/build/reports/lint-results-*.html`; the XML/CLI output alone is painful to read.
