# secrets/decrypt.sh

## What this is
This script decrypts every committed secret into its ignored, build-ready location on your machine. It reads the passphrase from the `GPG_KEY` env var when present (CI mode) or prompts invisibly otherwise (human mode), then runs `gpg --decrypt` once per file. It covers `secret.properties` plus all ten licensed font files, including the two Graphik weights that must land in two places.

## How it fits
Run this from the repo root right after cloning and again any time someone changes a secret upstream, since `git pull` never updates your ignored local files. Downstream, Gradle picks up `secret.properties` for signing/API config, and the app loads fonts from `pocket-ui/src/main/assets/` and `Pocket/src/main/res/font/`. GitHub PR workflows (`on-pull-request.yml`) and Bitrise (`bitrise.yml`) call this same script with `GPG_KEY` supplied as a secret env var.

## Key pieces
- **`decrypt() OUTPUT INPUT_DIR OUTPUT_DIR` helper** — exists to collapse eleven nearly identical `gpg` invocations into one line each; it maps `OUTPUT.gpg` in the input dir to plaintext `OUTPUT` in the output dir.
- **`GPG_KEY` prompt-or-env block** — exists so the same script works interactively (hidden `read -s` prompt, key never echoed or logged) and headlessly on CI.
- **Font destination trio (`FONTS_SECRETS`, `FONTS_ASSETS`, `FONTS_RES`)** — exists because most fonts ship as bundled assets, while `graphik_lcg_medium_no_leading` and `graphik_lcg_regular_no_leading` are additionally needed as Android `res/font` resources the layout system can reference by ID.
- **Repeated `graphik` decrypt lines** — not a copy-paste bug: those two weights are intentionally decrypted twice, once per destination above.

## Junior notes
- `gpg --quiet --batch --yes` means decrypt without interaction or extra output, overwriting any stale local copy — safe because the local copy is regenerable ciphertext output.
- If decryption fails with a bad-passphrase error, you pasted the wrong key from 1Password; there is nothing to fix in the repo.
