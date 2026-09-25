# Pocket/src/main/java/com/pocket/sdk2/analytics/context/ContextualRootBinder.java
## What this is
A small helper class that implements the storage side of `ContextualRoot`: it keeps a map from `View` to its bound `Contextual`. Screens that implement `ContextualRoot` delegate to an instance instead of writing their own map. It returns the bound context or null when nothing was bound.
## How it fits
Owned by `AbsPocketActivity` / `AbsPocketFragment` (both `ContextualRoot`s). `bindViewContext()` writes into it; `Interaction`'s view-tree walk reads via `getActionContextFor()`. It sits purely between the screen code that declares context and the analytics code that collects it.
## Key pieces
- `views` (`WeakHashMap<View, Contextual>`) — the binding table. WHY a weak map: entries vanish automatically when a view is garbage-collected, so recycled/destroyed views do not leak the activity.
- `bindViewContext(View, Contextual)` — stores the binding. WHY: the write side of the `ContextualRoot` contract.
- `getActionContextFor(View)` — returns the bound provider's `ActionContext`, or null. WHY: null means "no opinion", and `Interaction` just skips that level.
## Junior notes
- `WeakHashMap` keys are weak: if the only reference to a view is this map, the entry disappears — that is intentional leak protection, not a bug.
- This class is not thread-safe by contract; in practice all calls happen on the main (UI) thread where views live.
