# secrets/fonts/blanco_osf_bold.otf.gpg

## What this is
This is the encrypted Bold weight of Blanco, Pocket's licensed serif reading typeface. Like all files here it is GPG symmetric AES-256 ciphertext; the actual font bytes are never visible in the repo. Blanco is the warm, book-like face used for article body text and headlines in the reader.

## How it fits
`secrets/decrypt.sh` restores this to `pocket-ui/src/main/assets/blanco_osf_bold.otf`, where `Fonts.java` loads it at runtime for bold serif rendering in ArticleView and related screens. Local F-Droid-style builds without secrets fall back to the system `serif` family instead. To replace the font, swap the plaintext asset and re-run `secrets/encrypt.sh`.
