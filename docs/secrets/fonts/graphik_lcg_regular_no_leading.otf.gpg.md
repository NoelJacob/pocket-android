# secrets/fonts/graphik_lcg_regular_no_leading.otf.gpg

## What this is
This is the encrypted Regular (base) weight of Graphik LCG, Pocket's licensed sans-serif interface typeface. It is GPG symmetric AES-256 ciphertext; the font bytes never appear in the repo. Regular is the default UI face — most labels, lists, and settings text are set in this cut.

## How it fits
`secrets/decrypt.sh` restores this file to two places: `pocket-ui/src/main/assets/` for runtime loading by `Fonts.java`, and `Pocket/src/main/res/font/` so layouts and styles can reference it as an Android font resource. Without decrypted secrets both paths fall back to system `sans-serif`. To replace it, swap the plaintext files and re-run `secrets/encrypt.sh`.
