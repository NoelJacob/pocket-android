# secrets/fonts/graphik_lcg_bold_no_leading.otf.gpg

## What this is
This is the encrypted Bold weight of Graphik LCG, Pocket's licensed sans-serif interface typeface ("no leading" means its vertical metrics are trimmed for tight UI layout). It is GPG symmetric AES-256 ciphertext; the font bytes never appear in the repo. This cut is used for strong emphasis in the UI: titles, buttons, and selected states.

## How it fits
`secrets/decrypt.sh` restores this to `pocket-ui/src/main/assets/graphik_lcg_bold_no_leading.otf`, where `Fonts.java` loads it for bold sans rendering across the app (falling back to system `sans-serif` without secrets). To replace it, swap the plaintext asset and re-run `secrets/encrypt.sh`.
