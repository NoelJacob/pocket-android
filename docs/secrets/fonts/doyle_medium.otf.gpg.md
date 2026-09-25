# secrets/fonts/doyle_medium.otf.gpg

## What this is
This is the encrypted Medium weight of Doyle, a licensed serif display face used for large headlines and editorial headers. It is GPG symmetric AES-256 ciphertext; no font data is visible in the repo. Doyle gives section titles and featured-card headlines their distinctive editorial look, separate from the Blanco body face.

## How it fits
`secrets/decrypt.sh` restores this to `pocket-ui/src/main/assets/doyle_medium.otf`, loaded at runtime by `Fonts.java` for headline rendering (falling back to system `serif` in secret-less builds). To replace it, swap the plaintext asset and re-run `secrets/encrypt.sh`.
