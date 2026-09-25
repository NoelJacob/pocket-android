# Pocket/src/test/java/com/pocket/test/MainCoroutineRule.kt
## What this is
JUnit4 `TestWatcher` rule that swaps the Main dispatcher (the UI-thread background-task dispatcher) for a test dispatcher before each test and resets it after. The JUnit4 counterpart to `BaseCoroutineTest`, for tests using `@get:Rule`.
## How it fits
Applied as `@get:Rule val mainDispatcherRule = MainDispatcherRule()` in JUnit4 suites like `AddUrlBottomSheetViewModelTest`. Accepts an injectable `TestDispatcher` (defaults to `UnconfinedTestDispatcher`) so tests can substitute a standard dispatcher when ordering matters.
## Key pieces
- `MainDispatcherRule(testDispatcher)` — rule holding the dispatcher; WHY: per-test Main replacement without a base class.
- `starting()` — `Dispatchers.setMain(testDispatcher)`; `finished()` — `Dispatchers.resetMain()`; WHY: setup/teardown symmetry prevents cross-test leaks.
## Junior notes
- Use this OR `BaseCoroutineTest`, never both — both swap Main and would conflict.
- `UnconfinedTestDispatcher` (default) runs eagerly; pass a `StandardTestDispatcher` plus `runTest` if you need explicit scheduling control.
