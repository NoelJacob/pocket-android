# Pocket/src/main/res/values/braze.xml

## What this is

This file held the Braze (push/engagement SDK) configuration: API key, endpoint, and push-handling flags, with production values overridden per build type. It is deleted from the working tree in this stripped variant but still tracked in git history.

## How it fits

The manifest and init code used to read these keys at startup; with the vendor removed there is no consumer left, which is why the file could go. See `git show HEAD:Pocket/src/main/res/values/braze.xml` for the last contents.
