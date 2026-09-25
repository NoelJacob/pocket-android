# Pocket/src/main/res/layout/fragment_home.xml

## What this is

This layout is the Home screen: sign-in banner, recent saves, and topic/slate recommendation sections.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `HomeFragment` (databinding = XML layouts bound to ViewModel fields, so the generated `FragmentHomeBinding` class wires views to code).

ViewModels `HomeViewModel` (`viewModel`) feed observable state into the layout, and the layout calls back into them (e.g. click handlers).

Reuses shared chunks: `view_home_recent_saves.xml`.

Included or previewed by: `main_graph.xml`.

## Key pieces

- `viewModel` (com.pocket.app.home.HomeViewModel): databinding source; the layout reads its observable UI state and forwards user actions to it.
- `@id/swipeRefreshLayout` (`androidx.swiperefreshlayout.widget.SwipeRefreshLayout`): structural container for positioning children
- `@id/scrollView` (`com.pocket.ui.view.themed.ThemedNestedScrollView`): scrollable container
- `@id/centerGuideline` (`View`): structural container for positioning children
- `@id/homeLabel` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/upgrade_icon` (`com.pocket.ui.view.button.IconButton`): interactive element the host fragment/adapter wires up
- `@id/signInBanner` (`com.pocket.app.home.views.SignInBanner`): structural container for positioning children
- `@id/recentSavesLayout` (`include`): structural container for positioning children
- `@id/slatesLayout` (`com.pocket.ui.view.themed.ThemedFrameLayout`): structural container for positioning children
- `@id/slatesSkeleton` (`com.pocket.app.home.loading.SlatesSkeletonView`): structural container for positioning children
- `@id/slatesRecyclerView` (`androidx.recyclerview.widget.RecyclerView`): content region updated by the host
- ...plus 3 more ids (dividers, spacers, constraints).

## Junior notes

- Databinding (`<layout>`/`<data>`): the build generates a `Binding` class; always set its lifecycle owner so observable state actually updates the UI.
