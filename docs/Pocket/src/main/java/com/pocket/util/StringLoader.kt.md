# Pocket/src/main/java/com/pocket/util/StringLoader.kt

## What this is
A two-method interface that reads string resources (including plurals) without handing Android `Context` objects to the caller. It solves the testability problem: ViewModels need display strings but must stay unit-testable without Android. For example, `HomeViewModel` and `MyListViewModel` take a `StringLoader` in their constructors, and `RecommendationUiState.toRecommendationUiState()` takes one as a parameter to build "X min read" labels.

## How it fits
It is constructed once in `PocketModule.provideStringLoader()` from the application context and provided app-wide as a singleton via Hilt DI (constructor params provided automatically). Consumers include `HomeViewModel`, `SlateDetailsViewModel`, `TopicDetailsViewModel`, `MyListViewModel`, `ItemOverflowBottomSheetViewModel`, and the `RecommendationUiState` mapping functions. Under the hood it delegates to `Context.getString` and `Resources.getQuantityString`.

## Key pieces
- `getString(resourceId)`: resolves a plain string resource. WHY it exists: the most common ViewModel string need, behind a fakeable seam.
- `getQuantityString(id, quantity, formatArgs)`: resolves a plural resource. WHY it exists: plural rules differ per language, so they must go through Android resources, not hand-rolled `if (n == 1)` logic.
- `invoke(context)`: companion factory (`StringLoader(context)`) binding the interface to a real context. WHY it exists: keeps the Android dependency in the DI module, out of ViewModels.

## Junior notes
- In tests, implement this interface with a stub returning fixed strings — that is the whole point; never pass a real context into a ViewModel just for strings.
- `@StringRes`/`@PluralsRes` are lint annotations that flag wrong resource types at build time.
- This mirrors `DrawableLoader`; strings and drawables are the two resources ViewModels most often need.
