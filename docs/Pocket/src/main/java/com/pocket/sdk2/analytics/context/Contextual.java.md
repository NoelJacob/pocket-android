# Pocket/src/main/java/com/pocket/sdk2/analytics/context/Contextual.java
## What this is
A one-method interface any view or component can implement to report its analytics context (an `ActionContext`: the where/why behind a user action). It exists so analytics code can ask "what screen or element did this action come from?" without knowing the concrete view class. It returns the current `ActionContext` for that component.
## How it fits
Implemented by custom views and by base classes like `AbsPocketActivity` / `AbsPocketFragment` (which are also `ContextualRoot`s). `Interaction.on(View)` checks `view instanceof Contextual` while walking up the view tree, and merges each level's context into the action being recorded. Alternative for non-custom views is `ContextualRoot.bindViewContext()`.
## Key pieces
- `getActionContext()` — returns this component's contribution to the action context; `Interaction` merges these bottom-up so values set closer to the tapped view win. WHY: lets every layer (button, fragment, activity, app) add its own fields.
## Junior notes
- `ActionContext` is a sync-engine "thing" (a plain data object with a builder), not an Android `Context` — the similar names are confusing.
- This interface never fires analytics itself; it only supplies context that `Interaction` attaches to an `Action` (a background task describing something the user did) when it is created.
