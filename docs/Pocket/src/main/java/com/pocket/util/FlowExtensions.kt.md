# Pocket/src/main/java/com/pocket/util/FlowExtensions.kt

## What this is
Four tiny helpers that remove boilerplate when working with Kotlin `StateFlow` (an observable holder of UI state that emits updates to collectors). They solve two repetitive chores: atomically tweaking a `MutableStateFlow` value in place, and collecting a flow without hand-writing coroutine-launch or lifecycle code. For example, a ViewModel writes `_uiState.edit { copy(loading = true) }` instead of `_uiState.update { ... }`, and a fragment writes `viewModel.events.collectWhenResumed(viewLifecycleOwner) { handleEvent(it) }`.

## How it fits
This file is the collection-facing companion to `LifecycleOwnerExtensions` (`repeatOnResumed`/`repeatOnCreated`, which it delegates to). `edit` is used by ViewModels such as `HomeViewModel`, `SlateDetailsViewModel`, `TopicDetailsViewModel`, and `ItemOverflowBottomSheetViewModel`. `collectWhenResumed` is used by fragments including `HomeFragment`, `DetailsFragment`, `MyListFragment`, `RecentSavesOverflowFragment`, and `ReportItemBottomSheetFragment`; list adapters (`DetailsAdapter`, `RecentSavesAdapter`, `SlatesAdapter`, `TopicsAdapter`) use the underlying `repeatOnCreated` path.

## Key pieces
- `edit(block)`: applies a transform to the current `MutableStateFlow` value via `update`. WHY it exists: shorter call sites for the extremely common "mutate state in place" pattern.
- `collect(coroutineScope, collector)`: launches collection of the flow in the given scope. WHY it exists: one-liner for fire-and-forget collection outside lifecycle owners.
- `collectWhenResumed(lifecycleOwner, collector)` / `collectWhenCreated(lifecycleOwner, collector)`: collect only while the lifecycle is at least RESUMED/CREATED, auto-stopping otherwise. WHY they exist: prevents wasted work and crashes from updating destroyed views.

## Junior notes
- `Flow`/`StateFlow`/`SharedFlow` are coroutines-based observable streams (background-task-friendly event pipes); collecting must happen inside a coroutine, which is why these helpers launch one for you.
- Prefer `collectWhenResumed` for UI events and `collectWhenCreated` for data that should keep flowing while the view exists but is not visible (adapter lists use CREATED).
- `edit` takes `T.() -> T` (a lambda with receiver), so inside the braces `this` is the current state — that is what enables the `copy(...)` style.
