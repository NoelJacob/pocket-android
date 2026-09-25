# secrets/fonts/graphik_lcg_medium_italic_no_leading.otf.gpg

## What this is
This is the encrypted Medium-Italic weight of Graphik LCG, Pocket's licensed sans-serif interface typeface. It is GPG symmetric AES-256 ciphertext; no font data is visible in the repo. It covers italic emphasis at medium weight in UI surfaces — a rarely used but brand-required cut for styled labels and quotes.

## How it fits
`secrets/decrypt.sh` restores this to `pocket-ui/src/main/assets/graphik_lcg_medium_italic_no_leading.otf`, loaded by `Fonts.java` for medium-italic sans rendering (system `sans-serif` fallback without secrets). To replace it, swap the plaintext asset and re-run `secrets/encrypt.sh`.
