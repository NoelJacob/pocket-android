# Pocket/src/main/java/com/pocket/sdk2/analytics/context/ContextualRoot.java
## What this is
An extension of `Contextual` for containers (activities, fragments) that can also hold context on behalf of child views that do not implement `Contextual` themselves. It adds a bind/lookup pair so any plain `View` can be given an `ActionContext`. It is the mechanism that makes context work for stock Android widgets.
## How it fits
Implemented by `AbsPocketActivity` and `AbsPocketFragment`. Screens call `bindViewContext(view, contextual)` for interesting children; later `Interaction.on(view)` looks up the `ContextualRoot` via `findContext()` and calls `getActionContextFor(view)` at each level of the view-parent walk, merging results into the recorded action.
## Key pieces
- `bindViewContext(View, Contextual)` — assigns a context provider to a plain view. WHY: avoids subclassing every button just for analytics.
- `getActionContextFor(View)` — retrieves the bound context for a view during an interaction walk. WHY: read side of the binding.
- Inherited `getActionContext()` — the root's own (screen/app-level) context, merged in by `Interaction.addAppContext()`.
## Junior notes
- A `ContextualRoot` is found through the Android `Context` chain (`ContextUtil.findContext`), so the activity hosting the view must implement it or no root context is collected.
- Bindings live only as long as the root implementation holds them (see `ContextualRootBinder`); rebinding is needed if views are recreated.
