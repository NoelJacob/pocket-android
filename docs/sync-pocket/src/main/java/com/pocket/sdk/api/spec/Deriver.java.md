# sync-pocket/src/main/java/com/pocket/sdk/api/spec/Deriver.java

## What this is

This is part of the sync spec engine: the rulebook that applies server results to local state and derives follow-on changes. It is pure logic with no networking, which makes sync behavior deterministic and testable.

## How it fits

Pocket invokes it after a Source returns: Applier writes server state into local Things, Deriver computes what else must change, and PocketSpec declares the rules they follow. SublistUtil is a small collection helper they share.

## Key pieces

- `Deriver` (class, line 97) — Deriving a field is mostly opt-in, otherwise it relies on syncing with v3 to fill in those values.
- `spec` (fun, line 107) — entry point other code calls; see callers for context.
- `cleanFeed` (fun, line 118) — <ul>
- `cleanRecommendations` (fun, line 140) — Like {@link #cleanFeed(List, Space.Selector)}, but also removes posts from profiles user
- `isReported` (fun, line 156) — entry point other code calls; see callers for context.
- `isDeleted` (fun, line 160) — entry point other code calls; see callers for context.
- `notFollowingAuthor` (fun, line 165) — entry point other code calls; see callers for context.
- `cleanPosts` (fun, line 170) — @return a copy of the list (or null if already null) that has filtered out any deleted posts.
- `derive__Item__badge` (fun, line 188) — entry point other code calls; see callers for context.
- `derive__Item__posts` (fun, line 205) — entry point other code calls; see callers for context.
- `derive__Profile__follow_count` (fun, line 210) — entry point other code calls; see callers for context.

## Junior notes

- Read the file top to bottom once; it is small and its declaration order follows its logic.

Names you will also see here: `PocketDerives`, `AdzerkPlacementName`, `ItemContentType`, `ItemSortKey`, `ItemStatus`, `ItemStatusKey`.
