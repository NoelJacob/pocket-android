# Pocket/src/main/java/com/pocket/app/Forgetter.kt
## What this is
A one-shot cleanup routine, a Kotlin `object` (language-level singleton, no injection needed), invoked from `App.onCreate`. It releases stale sync-engine subscriptions ("holds": named persistent queries the sync layer keeps up to date) for long-removed features, and deletes dead preference keys. Without it, upgrades would keep syncing data for screens that no longer exist and accumulate orphaned prefs forever.
## How it fits
Runs once per process start before normal sync work: `pocket.forget(holder, query)` drops each retired hold (old AppSync item caps, Discover feed versions, slate lineups, Adzerk placements, notifications, home slates) and `prefs.remove(...)` drops keys from removed surveys, tooltips, offline settings, reader flags, and experiments. It is upstream of everything: nothing calls back into it.
## Key pieces
- `forget(pocket, prefs)`: the single entry point; WHY one function instead of per-feature migrations is that these are fire-and-forget tombstones whose context would otherwise be lost across refactors.
- `Holder.persistent(name)` + query pairs: identify exactly the subscription to release; the name MUST match the original subscriber or the hold survives.
- `prefs.remove(key)` lines grouped by retired feature: each group documents which dead UI the keys belonged to.
## Junior notes
- This file is append-only: when you remove a synced subscription or a pref, add its cleanup here and never delete old lines, since users may upgrade from versions years old.
- `pocket.forget` with no query drops every hold under that name; the query-bearing overloads drop only the matching subscription, so check which form you need.
