# Pocket/src/main/assets/html/j/diff_match_patch.js

## What this is

This is a vendored, minified copy of Google's diff-match-patch library, which compares two strings and produces insert, delete, and equal operations, plus fuzzy text matching and patch apply. `diff-match-patch` is an external open-source library, not Pocket code, and the minified single line keeps the asset bundle small.

## How it fits

Loaded by all three `article-mobile*.html` shells for use by `j/highlighting.js`, which since 2018 uses this library to find saved annotation text in the article even when the page text shifted slightly (per the header comment citing Pocket/Android PR #575). It exposes the global `diff_match_patch` constructor with `diff_main`, `match_main`, and `patch_make` / `patch_apply` methods.
