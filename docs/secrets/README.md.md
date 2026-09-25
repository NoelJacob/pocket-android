# secrets/README.md

## What this is
This is the onboarding guide for the repo's secret handling: how to get secrets when you clone, how to add or change one, and how CI does the same thing. The core idea is simple — encrypted files are committed, decrypted files live only on your machine. The key itself lives in the Android team's 1Password vault (or the `GPG_KEY` env var on CI), never in the repo.

## How it fits
New developers follow this doc to run `secrets/decrypt.sh` before their first build, since without `secret.properties` and the licensed fonts the app cannot sign release builds or render its branded typefaces. When someone rotates an API key or adds a secret, they edit the local decrypted file and run `secrets/encrypt.sh` to update the committed `.gpg` file. GitHub workflows and Bitrise both rely on this same contract, providing `GPG_KEY` as a redacted env var and running the same scripts.

## Key pieces
- **Get-secrets flow (clone or secret changed): run `secrets/decrypt.sh`** — exists so a developer's ignored local files always match the latest committed ciphertext without needing to understand GPG.
- **Add-a-secret flow (edit local file, then `secrets/encrypt.sh`)** — exists so changes flow one direction (plaintext on disk → ciphertext in git) and reviewers only ever see the `.gpg` diff.
- **`GPG_KEY` env var convention** — exists so CI machines can decrypt non-interactively while humans get an invisible prompt; one mechanism serves both.

## Junior notes
- `gpg` (GNU Privacy Guard, a command-line encryption tool) must be installed first; on macOS that means `brew install gnupg`.
- The passphrase prompt hides your typing and keeps the key out of shell history — that is deliberate, not a broken terminal.
