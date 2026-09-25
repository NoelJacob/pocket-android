# Pocket/src/main/java/com/pocket/sdk/util/UniversalLinks.java
## What this is
A deprecated alias for DeepLinks, kept only so older code and searches for "Universal Links" still resolve. It adds no behavior of its own.
## How it fits
Subclasses DeepLinks, so anywhere UniversalLinks was referenced now just uses the DeepLinks factories and Parser. New code should reference DeepLinks directly.
## Key pieces
- `UniversalLinks extends DeepLinks`: pure proxy; the @Deprecated marker steers callers to DeepLinks.
## Junior notes
- Deprecated = still compiles but flagged for removal; treat any usage as a rename to DeepLinks on next touch.
