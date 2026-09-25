# secrets/fonts/graphik_lcg_medium_no_leading.otf.gpg

## What this is
This is the encrypted Medium weight of Graphik LCG, Pocket's licensed sans-serif interface typeface. It is GPG symmetric AES-256 ciphertext; the font bytes never appear in the repo. Medium is the workhorse UI weight — list titles, toolbar text, and body copy in sans mode.

## How it fits
`secrets/decrypt.sh` restores this file to two places: `pocket-ui/src/main/assets/` for runtime loading by `Fonts.java`, and `Pocket/src/main/res/font/` so Android XML layouts can reference it as a font resource by ID. Secret-less builds fall back to system `sans-serif` in both paths. To replace it, swap the plaintext files and re-run `secrets/encrypt.sh`.
