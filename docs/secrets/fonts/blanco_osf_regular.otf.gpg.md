# secrets/fonts/blanco_osf_regular.otf.gpg

## What this is
This is the encrypted Regular (base) weight of Blanco, Pocket's licensed serif reading typeface. It is GPG symmetric AES-256 ciphertext; the font bytes never appear in the repo. This is the default serif face — the one most article body text is set in when the user picks a serif reading theme.

## How it fits
`secrets/decrypt.sh` restores this to `pocket-ui/src/main/assets/blanco_osf_regular.otf`, where `Fonts.java` loads it as the standard serif option for the reader (with `Fonts.get()` mapping to system `serif` when secrets are absent). To replace it, swap the plaintext asset and re-run `secrets/encrypt.sh`.
