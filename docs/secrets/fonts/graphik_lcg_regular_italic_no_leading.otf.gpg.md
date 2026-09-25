# secrets/fonts/graphik_lcg_regular_italic_no_leading.otf.gpg

## What this is
This is the encrypted Regular-Italic weight of Graphik LCG, Pocket's licensed sans-serif interface typeface. It is GPG symmetric AES-256 ciphertext; no font data is visible in the repo. It handles italic emphasis in regular-weight UI text, such as hints, captions, and quoted strings.

## How it fits
`secrets/decrypt.sh` restores this to `pocket-ui/src/main/assets/graphik_lcg_regular_italic_no_leading.otf`, loaded by `Fonts.java` for regular-italic sans rendering (system `sans-serif` fallback without secrets). To replace it, swap the plaintext asset and re-run `secrets/encrypt.sh`.
