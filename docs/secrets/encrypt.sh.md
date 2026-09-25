# secrets/encrypt.sh

## What this is
This is the mirror image of `decrypt.sh`: it re-encrypts your edited local secrets back into the committed `.gpg` files using symmetric AES-256 encryption. You only run it when you intentionally change a secret, such as rotating an API key or replacing a font file. Like decrypt, it takes the passphrase from `GPG_KEY` or an invisible prompt.

## How it fits
The workflow is edit-then-encrypt: change the ignored plaintext (e.g. `secrets/secret.properties` or a font in `pocket-ui/src/main/assets/`), run this from the repo root, and commit the resulting `.gpg` diffs for review. Teammates and CI then pick up your change via `decrypt.sh`. Note the direction is reversed from decrypt — sources live in the app directories and land in `secrets/` — and the script creates `secrets/fonts/` if it does not exist yet.

## Key pieces
- **`encrypt() INPUT INPUT_DIR OUTPUT_DIR` helper** — exists to keep all ten font encryptions plus `secret.properties` as one-liners; it writes `INPUT.gpg` next to the other ciphertext using `--cipher-algo AES256 --symmetric`.
- **Symmetric (passphrase, not keypair) encryption** — exists so the whole team shares one vaulted passphrase instead of managing per-developer public keys.
- **`mkdir "$FONTS_SECRETS"`** — exists so a fresh checkout or cleaned directory does not fail on the first font encryption.

## Junior notes
- Symmetric encryption means anyone with the passphrase can both encrypt and decrypt — that is why the key lives in 1Password, never in chat or code.
- Double-check `git status` after running: you should see only `.gpg` files changed, never plaintext.
