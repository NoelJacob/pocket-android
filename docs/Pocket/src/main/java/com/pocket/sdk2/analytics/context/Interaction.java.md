# Pocket/src/main/java/com/pocket/sdk2/analytics/context/Interaction.java
## What this is
The factory for analytics context: given the origin of a user action (a tapped `View`, an Android `Context`, or a `Contextual`), it builds the `ActionContext` plus timestamp that get attached to the fired `Action`. It walks up the view hierarchy and the Android context chain, merging every level's contribution. Callers then pass `interaction.context` into the new action.
## How it fits
Called at every user-action site, e.g. a button click handler calls `Interaction.on(buttonView)` and hands the resulting `context` to the archive/favorite/delete action constructor. It reads from `Contextual` views, `ContextualRoot` bindings (via `ContextualRootBinder`), and enclosing activity/application components, merging with `ThingUtil.merge()`. Downstream the context travels with the action through the sync engine to the server.
## Key pieces
- `on(View)` — builds context from a tapped view: walks parents collecting view context, then adds app context. WHY: the common case (tap → action).
- `on(Context androidContext)` — builds context with no view (background/service trigger). WHY: actions that have a screen context but no tapped widget.
- `on(Contextual, Context)` — starts from an explicitly provided context instead of walking views. WHY: custom components that already know their context.
- `context` + `time` fields — the collected result and when the interaction happened. WHY: stamped onto the action at creation.
- `addViewContext()` — recursive parent walk; values closer to the origin win the merge. WHY: a button's context overrides its screen's on conflicts.
- `addAppContext()` — merges root, hosting component (activity/service), and application contexts. WHY: every action also records which screen and app state it came from.
- `merge(ActionContext)` / `merge(Modifier)` — overrides collected values with custom ones. WHY: one-off corrections without rebuilding the whole walk.
## Junior notes
- Merge order matters: `ThingUtil.merge(base, override)` keeps the override's non-null fields — inner/closer values are passed as the override, so they win.
- The `TODO REVIEW` comment is accurate: the three `on()` overloads share little code; when editing one path, check whether the other two need the same fix.
