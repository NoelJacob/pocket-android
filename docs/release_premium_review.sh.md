# release_premium_review.sh

## What this is
This is a one-purpose helper that builds the premium-review APK variant for testing real Google Play purchases. It cleans and then assembles `assemblePremiumReviewTeamRelease`, a team-signing build where the Play Store recognizes whitelisted test accounts so purchase flows actually execute. The output lands at `Pocket/build/outputs/apk/premiumReview/teamRelease/`.

## How it fits
Run `bash release_premium_review.sh` from the repo root when you need to verify paywalls, upgrade prompts, or purchase restoration against Play's sandbox — normal debug builds cannot complete purchases because they are not signed for Play's test track. Only accounts whitelisted in the Play Console can buy in this build, so coordinate with the release captain for test access. This is unrelated to the F-Droid flavor, which removes billing entirely.

## Key pieces
- **`./gradlew clean` first** — exists to guarantee no stale purchase-flag state from a previous flavor leaks into the review build.
- **`assemblePremiumReviewTeamRelease` task** — the whole point: the `premiumReview` flavor crossed with the `teamRelease` signing config is the only combination Play's test purchasing accepts.

## Junior notes
- Team-signed release builds need decrypted secrets (`secrets/decrypt.sh`) first — the signing config reads from `secret.properties`.
- Never distribute this APK outside the whitelisted testers; it carries real-purchase capability.
