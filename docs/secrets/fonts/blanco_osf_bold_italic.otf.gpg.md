# secrets/fonts/blanco_osf_bold_italic.otf.gpg

## What this is
This is the encrypted Bold-Italic weight of Blanco, Pocket's licensed serif reading typeface. It is GPG symmetric AES-256 ciphertext; the font bytes themselves are never visible in the repo. This cut covers emphasized text inside already-bold serif passages, such as a stressed phrase in an article subheading.

## How it fits
`secrets/decrypt.sh` restores this to `pocket-ui/src/main/assets/blanco_osf_bold_italic.otf`, where `Fonts.java` loads it for bold-italic serif rendering in the reader. Builds without decrypted secrets fall back to system `serif` with synthetic styling. To replace it, swap the plaintext asset and re-run `secrets/encrypt.sh`.
