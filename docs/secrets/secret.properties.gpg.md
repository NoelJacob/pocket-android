# secrets/secret.properties.gpg

## What this is
This is the encrypted store for Gradle-level secrets: signing keystore passwords, third-party API keys, and service credentials the build needs but the public repo must never show. It is ciphertext produced by `secrets/encrypt.sh` (GPG symmetric AES-256) and is useless without the team passphrase. Only the property names and their purpose are public knowledge; values are never inspected or described.

## How it fits
`secrets/decrypt.sh` turns this file into the git-ignored `secrets/secret.properties` that Gradle's `getSecret()` reads at build time for release signing and vendor SDK configuration. Local debug builds tolerate its absence (empty-string fallback), but release, Bitrise, and GitHub CI builds require it. To change a value, edit the decrypted local file and re-run `encrypt.sh`, then commit the updated ciphertext.
