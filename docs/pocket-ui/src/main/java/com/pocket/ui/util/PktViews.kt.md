# pocket-ui/src/main/java/com/pocket/ui/util/PktViews.kt

## What this is
A single Kotlin extension function (a function callable as a method on `View`, here `view.updateEnabledAlpha()`) that dims a view to half opacity when disabled and restores full opacity when enabled. It gives disabled state an immediate visual cue on views whose backgrounds do not already restyle themselves.

## How it fits
Any view or button that toggles `isEnabled` calls `updateEnabledAlpha()` right after the change so the user sees the disabled state. Note the file lives under `util/` but declares `package com.pocket.ui.view.button`, so it reads as part of the button family and is imported from there.

## Key pieces
- `View.updateEnabledAlpha()`: sets `alpha` to `1f` when enabled, `0.5f` when disabled. Reads `isEnabled` at call time rather than observing it, so the caller must invoke it after every enabled change.

## Junior notes
- This is call-and-forget, not automatic: nothing observes `isEnabled` for you. If you set `isEnabled` without calling this, the alpha goes stale.
- Setting `alpha` on a parent view multiplies into its children, so calling this on a container dims the whole subtree. That is sometimes wanted, sometimes a surprise.
