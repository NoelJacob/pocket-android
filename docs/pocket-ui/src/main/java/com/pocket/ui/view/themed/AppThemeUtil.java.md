# pocket-ui/src/main/java/com/pocket/ui/view/themed/AppThemeUtil.java
## What this is
The lookup helper behind the whole View theming system. Given any view, it finds the nearest theme provider (a view, parent, Activity, or Application implementing Themed) and returns that provider's theme as drawable-state attributes. It also exposes the same lookup by Context for code like Compose that has no view.

## How it fits
Every Themed* view calls `AppThemeUtil.getState(this)` inside `onCreateDrawableState()` to merge the theme into its drawable state; the class javadoc is the setup guide (define state_light/state_dark attrs, subclass views, implement Themed at app or activity level, then write state-list colors). `findThemed(Context)` is what PocketTheme uses to bridge the View theme into Compose. Without this class, each themed view would reimplement the provider search.

## Key pieces
- `getState(view)` — public entry point; returns the provider's theme-state IntArray, or EMPTY when nothing provides a theme.
- `findThemed(view)` — private search: the view itself, then each parent view, then the Context chain.
- `findThemed(context)` — searches the Context hierarchy (via ContextUtil) and falls back to the application context, so Activity-level themes win over app-level ones.

## Junior notes
- Returning EMPTY instead of null keeps `mergeDrawableStates` call sites one line with no null checks.
- Theme resolution happens lazily per drawable-state build, so adding a Themed provider above a subtree re-themes it on the next state refresh.
