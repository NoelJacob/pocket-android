# Pocket/src/main/java/com/pocket/sdk/offline/cache/CacheState.java

## What this is
A small latch (a sticky on/off flag) remembering whether the offline cache is over the user's size cap and whether background downloading is currently locked out. It exists to break an endless loop: download → exceed cap → clean → fall under cap → download again, forever.

## How it fits
Owned by `Assets`; `invalidate()` rechecks sizes after writes, cleans, and setting changes. While locked, `Assets.isOfflineDownloadingRestricted()` returns true and `OfflineDownloading.isPredownloadingAllowed()` refuses new prefetch (explicit user-opened downloads with `ALWAYS` still go). The lock only clears on real change: an item removed, cache settings changed, process restart, or an asset user unregistered.

## Key pieces
- `invalidate()` — recomputes over-limit / over-buffer state after any cache event. WHY: single choke point so every mutation re-evaluates the lock consistently.
- `isOverLimit()` / `isOfflineDownloadingRestricted()` — raw size fact vs download policy. WHY: callers need both the measurement and the decision.
- `onItemRemoved()` / `onCacheLimitSettingsChanged()` — the legitimate unlock paths. WHY: freeing real space (or changing the cap) is the only safe time to resume prefetch.
- `setOfflineDownloadingLocked()` + `restartDownloading()` — flips the persisted lock pref and kicks `predownload()` on unlock. WHY: lock survives in prefs, but a fresh process starts unlocked.

## Junior notes
- The lock is package-scoped (`protected`, same-package access) on purpose: only `Assets` drives it. Do not touch `CacheState` directly from UI or downloaders.
- Being "under the limit again" after a clean does NOT unlock by itself — that is the anti-loop design. A newcomer expecting auto-resume will misread this.
