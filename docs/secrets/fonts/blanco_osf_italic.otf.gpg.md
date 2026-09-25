# secrets/fonts/blanco_osf_italic.otf.gpg

## What this is
This is the encrypted Italic (regular-weight) cut of Blanco, Pocket's licensed serif reading typeface. It is GPG symmetric AES-256 ciphertext; no font data is visible in the repo. This is the face used for pull quotes, captions, and inline emphasis inside serif article text.

## How it fits
`secrets/decrypt.sh` restores this to `pocket-ui/src/main/assets/blanco_osf_italic.otf`, loaded at runtime by `Fonts.java` for italic serif rendering in ArticleView. Secret-less builds substitute the system `serif` family. To replace it, swap the plaintext asset and re-run `secrets/encrypt.sh`.
