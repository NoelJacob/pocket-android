# sync-pocket/src/main/java/com/pocket/sdk/api/thing/VideoUtil.java

## What this is

This is a model helper for the sync layer: small static functions that read, build, or convert one part of the Pocket data model (items, tags, accounts, videos). It keeps model logic next to the model instead of scattered through sources.

## How it fits

Sources (V3Source, ClientApiSource) and the spec Applier/Deriver call these helpers when translating between server JSON and in-memory Things. Repositories upstream benefit without knowing the helpers exist.

## Key pieces

- `VideoUtil` (class, line 12) — Tools for working with {@link Video}
- `convertUrl` (fun, line 17) — entry point other code calls; see callers for context.
- `upgradeType` (fun, line 49) — If this video is {@link VideoType#IFRAME}, this method will check to see if the iframe source ({@link Video#type}) is actually pointing to a known format
- `getYouTubePattern` (fun, line 61) — entry point other code calls; see callers for context.
- `getVimeoPattern` (fun, line 69) — entry point other code calls; see callers for context.
- `makeYouTubeUrl` (fun, line 76) — entry point other code calls; see callers for context.

Concrete endpoints referenced here:

- `http://www.youtube.com/watch?v=`

## Junior notes

- Read the file top to bottom once; it is small and its declaration order follows its logic.

Names you will also see here: `VideoType`, `Video`.
