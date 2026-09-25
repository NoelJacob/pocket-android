# Pocket/src/main/java/com/pocket/util/android/LifecycleOwnerExtensions.kt

## What this is
Two one-line helpers, `repeatOnResumed` and `repeatOnCreated`, that run a coroutine block (a background task) only while a screen's lifecycle (its journey from created to started to resumed to destroyed) is at least RESUMED or CREATED, restarting it if the screen comes back. They solve the leak/wasted-work problem of collecting observable streams after a view is gone. For example, list adapters collect their ViewModel's state inside `viewLifecycleOwner.repeatOnCreated { viewModel.topicsUiState.collect { submitList(it) } }`.

## How it fits
They sit in the Android util package and underpin reactive UI collection app-wide. Direct callers include `DetailsAdapter`, `RecentSavesAdapter`, `SlatesAdapter`, and `TopicsAdapter` (all `repeatOnCreated` for list state), plus `MyListFragment` and `AddUrlBottomSheetFragment` (`repeatOnResumed` for timed/one-shot UI work). They are also the engine behind `FlowExtensions.collectWhenResumed/collectWhenCreated`, which most fragments call instead. Each returns the launched `Job` and delegates to `repeatOnLifecycle`.

## Key pieces
- `repeatOnResumed(block)`: launches the block in `lifecycleScope`, re-running it on every RESUMED stretch and cancelling below that. WHY it exists: UI-event collection that must pause when the screen is not in front.
- `repeatOnCreated(block)`: same, keyed on CREATED. WHY it exists: data collection (adapter lists) that should survive being covered by another screen but stop at destruction.

## Junior notes
- `lifecycleScope` is a coroutine scope tied to the LifecycleOwner that auto-cancels at destroy; `repeatOnLifecycle` additionally cancels/restarts the block as the state crosses the threshold — that restart behavior is why collectors must be idempotent.
- RESUMED means visible and interactive; CREATED means the view exists but may be hidden behind something. Adapter state uses CREATED so lists stay fresh while covered.
- Prefer the `FlowExtensions.collectWhen*` wrappers when collecting a flow; call these directly only for non-flow suspend work (like `MyListFragment`'s delay-then-hide).
