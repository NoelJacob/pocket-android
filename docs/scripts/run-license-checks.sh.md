# scripts/run-license-checks.sh

## What this is
This is a one-command wrapper that validates open-source license compliance (`./gradlew :Pocket:licensee :sync-gen:licensee`). The `licensee` Gradle plugin inventories every dependency's license and fails the build on anything disallowed (e.g. copyleft code linked into the proprietary app). It covers the app and the code-generator module, the two places third-party code enters the product.

## How it fits
The `on-pull-request.yml` workflow runs this in its `licenses` job on every PR, after environment setup and secret decryption. If a developer adds a dependency with an unacceptable license, this gate — not code review — is what catches it. The allowed/disallowed license lists live in each module's `licensee` configuration block, not in this script.

## Key pieces
- **`:Pocket:licensee :sync-gen:licensee` task pair** — exists to check both the shipped app's dependency closure and the build-time generator's closure; a bad license in either one blocks the PR.

## Junior notes
- Adding any new library means this check re-runs automatically on your PR — check the task output if CI flags the license.
- `set -e` makes the first failing module fail the whole script, so you see the violation immediately.
