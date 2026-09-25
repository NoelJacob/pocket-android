# Pocket/alpha.keystore

## What this is

A Java KeyStore file (2,062 bytes) holding the signing key used for alpha (internal team) builds of the Pocket app. Android requires every installable APK to be digitally signed, and this keystore is the identity those alpha builds are signed with.

## How it fits

Build tooling loads this keystore at signing time to sign the alpha APK/AAB, whose manifest label is overridden to "Pocket Dev" by `Pocket/src/team`. It lives at the `Pocket/` module root next to `build.gradle.kts` and is provenance-tracked in version control, so any team member can reproduce a signed alpha build. Never print, commit-derived, or inspect its contents (passwords, aliases, private keys); treat it as a secret credential, not source.
