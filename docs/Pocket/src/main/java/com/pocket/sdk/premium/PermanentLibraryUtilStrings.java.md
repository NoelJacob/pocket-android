# Pocket/src/main/java/com/pocket/sdk/premium/PermanentLibraryUtilStrings.java
## What this is
Holds the obfuscated secret salt that `PermanentLibraryUtil.hash()` mixes into its signature. The phrase fragments are scattered across arrays and fields and reassembled in a static block so the secret is not sitting as one readable string.
## How it fits
Used only by `PermanentLibraryUtil`: `sSaltBaseKey` plus the `":"` delimiter (`sDelim3`) and one `":"` fragment form the hash input. Nothing else in the app should reference this class.
## Key pieces
- `sSaltPieces` — string fragments of the phrase "Piles of books by the bedside" plus separators; indexed piecemeal so grep does not reveal the secret.
- `sSaltBaseKey` — the fully assembled salt, built once in the `static` block (code that runs once when the class first loads).
- `sDelim3` — the `":"` separator used between hash fields.
## Junior notes
- This is obfuscation, not real security: anyone can decompile it. Never put a true secret here; it only stops casual tampering.
- Fragments are referenced by magic index (e.g. `[18]`); if you reorder the array the hash changes and every link breaks, so do not touch it.
