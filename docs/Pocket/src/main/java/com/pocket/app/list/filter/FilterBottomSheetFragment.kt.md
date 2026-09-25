# Pocket/src/main/java/com/pocket/app/list/filter/FilterBottomSheetFragment.kt

## What this is
The sort-and-filter bottom sheet on the Saves list (Newest/Oldest/Shortest/Longest plus Viewed/Unviewed/Short/Long reads). It is pure UI shell — layout inflation plus ViewModel binding — with all logic in `FilterBottomSheetViewModel`.

## How it fits
Opened from the My List toolbar/filter button via `newInstance(savesTab)`; the `SavesTab` argument (which tab opened it) arrives as a fragment argument. The XML layout binds directly to `viewModel` with `lifecycleOwner`, so row taps call straight into the ViewModel, which writes through to `ListManager` and refreshes the list. Dismissing the sheet returns to the list, already re-sorted.

## Key pieces
- `viewModel by viewModels()` — WHY: Hilt DI (constructor params provided automatically) gives the fragment its scoped ViewModel.
- `onCreateView` — WHY: inflates `FragSortFilterBottomSheetBinding`, sets the lifecycle owner so `StateFlow` bindings update, and exposes the ViewModel to XML.
- `onViewCreated` → `viewModel.onInitialized(savesTab)` — WHY: hands the tab context over after the view exists; ordering matters because collection starts there.
- `newInstance(savesTab)` — WHY: the only safe constructor; bundles the tab enum so rotation restores it.

## Junior notes
- This is a `BottomSheetDialogFragment`: it floats over the list and dismisses on swipe-down — no navigation graph entry needed.
- `_binding = null` in `onDestroyView` is required to avoid leaking the view hierarchy; never touch `binding` after that point.
