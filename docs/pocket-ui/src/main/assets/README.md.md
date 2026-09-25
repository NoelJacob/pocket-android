# pocket-ui/src/main/assets/README.md

## What this is

The recipe for Pocket's Android font fix: the Graphik files were edited to zero out the 200-unit leading and fold that 20% into ascent (818→982) and descent (182→218), because Android ignores font leading and article text came out with wrong line spacing. It lists the exact FontForge steps (OS/2 metrics, uncheck "Really use Typo metrics", regenerate as `.otf`).

## How it fits

It explains the provenance of the `*_no_leading.otf` files ignored by `pocket-ui/src/main/assets/.gitignore` and referenced by `graphik-lcg-mobile.css`, which the reader WebView templates load. Anyone re-licensing or updating Graphik must repeat this procedure or the reader regresses to cramped lines.
