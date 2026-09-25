# Pocket/src/main/res/layout/activity_main.xml

## What this is

This layout is the single-activity host: the NavHostFragment container plus bottom navigation that swaps every top-level screen.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `MainActivity` (databinding = XML layouts bound to ViewModel fields, so the generated `ActivityMainBinding` class wires views to code).

ViewModels `MainViewModel` (`viewModel`) feed observable state into the layout, and the layout calls back into them (e.g. click handlers).

## Key pieces

- `viewModel` (com.pocket.app.MainViewModel): databinding source; the layout reads its observable UI state and forwards user actions to it.
- `@id/fragmentContainer` (`androidx.fragment.app.FragmentContainerView`): structural container for positioning children
- `@id/fetchingBackground` (`com.pocket.ui.view.themed.ThemedView`): structural container for positioning children
- `@id/bottomNav` (`com.pocket.ui.view.themed.ThemedLinearLayout`): structural container for positioning children
- `@id/home` (`com.pocket.ui.view.menu.BottomNavigationButton`): interactive element the host fragment/adapter wires up
- `@id/myList` (`com.pocket.ui.view.menu.BottomNavigationButton`): interactive element the host fragment/adapter wires up
- `@id/settings` (`com.pocket.ui.view.menu.BottomNavigationButton`): interactive element the host fragment/adapter wires up

## Junior notes

- Databinding (`<layout>`/`<data>`): the build generates a `Binding` class; always set its lifecycle owner so observable state actually updates the UI.
