# Pocket/src/main/java/com/pocket/sdk/api/SendToFriend.java

## What this is
Wires up the legacy Send-to-Friend data (friend list, recent friends, email autocomplete) so it stays persisted locally and synced with the server. It is mostly a setup class with no UI: on app start it registers those three data models with the sync layer and asks `AppSync` to include the friend/share flags in its server requests. The feature itself is being deprecated; sending is already removed, but receiving and viewing old items still works.

## How it fits
Injected as a singleton at startup with `Pocket` and `AppSync`. Inside `pocket.setup(...)` it calls `pocket.remember(...)` plus `pocket.initialize(...)` for the `friends`, `recentFriends`, and `autoCompleteEmails` things under a `"stf"` holder, meaning their state survives restarts and participates in sync. The `addInitialFlags`/`addFlags` calls make the shared `AppSync` `Get` request fetch mail/share sections, so this feature never issues its own sync.

## Key pieces
- `SendToFriend(Pocket pocket, AppSync appsync)` — the only real code: registers the three things and the sync flags during construction. Exists so the feature bootstraps itself purely by being injected.
- `Holder.persistent("stf")` — the storage bucket these three models share. Exists so their cached state is namespaced and survives process death.
- `addInitialFlags(g -> g.forcemails(1))` / `addFlags(g -> g.shares(1))` — requests full friend data on first fetch and share deltas afterwards. Exists to fold this feature's needs into the one shared sync request.

## Junior notes
- `remember` = keep this model cached locally; `initialize` = make sure it has an initial value. Most synced models need both calls at startup.
- A sync here means the standard `AppSync` pull: local cached friends are the fast path, the server response refreshes them. Do not add new sending UI; the feature is in removal phase 1.
