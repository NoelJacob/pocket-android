# Pocket/src/main/res/layout/fragment_home_details.xml

## What this is

This layout is the Home details screen: the full story list behind one slate or topic card.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `DetailsFragment`, `SlateDetailsFragment`, `TopicDetailsFragment` (databinding = XML layouts bound to ViewModel fields, so the generated `FragmentHomeDetailsBinding` class wires views to code).

ViewModels `DetailsViewModel` (`viewModel`) feed observable state into the layout, and the layout calls back into them (e.g. click handlers).

Included or previewed by: `main_graph.xml`.

## Key pieces

- `viewModel` (com.pocket.app.home.details.DetailsViewModel): databinding source; the layout reads its observable UI state and forwards user actions to it.
- `@id/appBar` (`com.pocket.ui.view.AppBar`): structural container for positioning children
- `@id/scrollView` (`com.pocket.ui.view.themed.ThemedNestedScrollView`): scrollable container
- `@id/recyclerView` (`androidx.recyclerview.widget.RecyclerView`): content region updated by the host
- `@id/skeleton` (`com.pocket.app.home.loading.DetailsSkeletonView`): structural container for positioning children
- `@id/recyclerViewOrSkeleton` (`androidx.constraintlayout.widget.Barrier`): structural container for positioning children
- `@id/bookAnimationView` (`com.pocket.ui.view.info.FeedFooterView`): structural container for positioning children

## Junior notes

- Databinding (`<layout>`/`<data>`): the build generates a `Binding` class; always set its lifecycle owner so observable state actually updates the UI.
