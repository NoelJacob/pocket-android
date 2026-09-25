# sync-pocket/src/main/java/com/pocket/sdk/api/spec/Applier.java

## What this is

This is part of the sync spec engine: the rulebook that applies server results to local state and derives follow-on changes. It is pure logic with no networking, which makes sync behavior deterministic and testable.

## How it fits

Pocket invokes it after a Source returns: Applier writes server state into local Things, Deriver computes what else must change, and PocketSpec declares the rules they follow. SublistUtil is a small collection helper they share.

## Key pieces

- `Applier` (class, line 133) — Implementation of all Pocket actions.
- `Edit` (interface, line 947) — core type of this file; callers reference it by name.
- `spec` (fun, line 136) — entry point other code calls; see callers for context.
- `logout` (fun, line 141) — entry point other code calls; see callers for context.
- `notification_action` (fun, line 146) — entry point other code calls; see callers for context.
- `notification_push_action` (fun, line 151) — entry point other code calls; see callers for context.
- `notificationAction` (fun, line 158) — Implementation for both {@link #notification_push_action(NotificationPushAction, Space)} and {@link #notification_action(NotificationAction, Space)}
- `add` (fun, line 177) — entry point other code calls; see callers for context.
- `readd` (fun, line 222) — entry point other code calls; see callers for context.
- `archive` (fun, line 227) — entry point other code calls; see callers for context.
- `delete` (fun, line 236) — entry point other code calls; see callers for context.
- `favorite` (fun, line 244) — entry point other code calls; see callers for context.

## Junior notes

- Read the file top to bottom once; it is small and its declaration order follows its logic.

Names you will also see here: `PocketApplier`, `Acctchange`, `Add`, `AddAlias`, `AddAnnotation`, `ApproveAccess`.
