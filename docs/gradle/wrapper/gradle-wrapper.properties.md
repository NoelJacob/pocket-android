# gradle/wrapper/gradle-wrapper.properties

## What this is
This six-line file pins the Gradle version every build uses: distribution 8.14.3 (the `-all` variant with sources and docs), fetched from `services.gradle.org`, with a 10-second network timeout and URL validation enabled. It is the only file you touch to upgrade Gradle — the wrapper jar and scripts read it at startup.

## How it fits
When `./gradlew` runs, the wrapper jar reads `distributionUrl` here, downloads that Gradle into `~/.gradle/wrapper/dists` on first use, and launches it. GitHub's `setup-gradle` action and Bitrise both respect this pin, so local and CI builds share the exact same Gradle. Renovate's `gradle-wrapper` manager proposes updates to this URL automatically.

## Key pieces
- **`distributionUrl` (gradle-8.14.3-all.zip)** — the version pin itself; `-all` includes IDE-supporting sources versus the smaller `-bin` flavor.
- **`validateDistributionUrl=true` + `networkTimeout=10000`** — integrity and snappiness guards: refuse tampered URLs and fail fast on stalled downloads.
- **`distributionBase/Path` and `zipStoreBase/Path` (`GRADLE_USER_HOME`, `wrapper/dists`)** — cache locations keeping downloaded distributions out of the repo.

## Junior notes
- The `\:` escaping in the URL is properties-file syntax for a literal colon — do not "fix" it to `://`.
- First run after a version bump re-downloads the full distribution (~120 MB), so it looks stalled while it is just fetching.
