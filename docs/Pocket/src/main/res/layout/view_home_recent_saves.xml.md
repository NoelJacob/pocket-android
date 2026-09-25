# Pocket/src/main/res/layout/view_home_recent_saves.xml

## What this is

This layout is the Recent Saves strip on Home: horizontal cards of the newest saves.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

ViewModels `RecentSavesViewModel` (`recentSavesViewModel`) feed observable state into the layout, and the layout calls back into them (e.g. click handlers).

Included or previewed by: `fragment_home.xml`.

## Key pieces

- `recentSavesViewModel` (com.pocket.app.home.saves.RecentSavesViewModel): databinding source; the layout reads its observable UI state and forwards user actions to it.
- `@id/centerGuideline` (`View`): structural container for positioning children
- `@id/recentSavesTitle` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/recentSavesSeeAllLayout` (`com.pocket.ui.view.themed.ThemedLinearLayout`): structural container for positioning children
- `@id/savesRecyclerView` (`androidx.recyclerview.widget.RecyclerView`): content region updated by the host

## Junior notes

- Databinding (`<layout>`/`<data>`): the build generates a `Binding` class; always set its lifecycle owner so observable state actually updates the UI.
