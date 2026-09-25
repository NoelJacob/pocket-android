# Pocket/src/test/java/com/pocket/BaseCoroutineTest.kt
## What this is
Base class for coroutine-based unit tests. It swaps the Main dispatcher (a special background-task thread reserved for UI work) for an `UnconfinedTestDispatcher` before each test and resets it after. Almost every ViewModel test extends it so suspend functions and state streams run synchronously without a device.
## How it fits
Extended by tests such as `MainViewModelTest`, `HomeViewModelTest`, `MyListViewModelTest`, and `ReaderViewModelTest`. It provides the `setupDispatcher` / `tearDownDispatcher` lifecycle around each test; tests using JUnit4 `Rule` plumbing use `MainDispatcherRule` instead.
## Key pieces
- `BaseCoroutineTest` — open base class so individual test classes inherit the dispatcher swap with no extra setup.
- `setupDispatcher()` (`@BeforeTest`) — calls `Dispatchers.setMain(UnconfinedTestDispatcher())`, redirecting Main-dispatcher coroutines (background tasks) to a test dispatcher that executes immediately.
- `tearDownDispatcher()` (`@AfterTest`) — calls `Dispatchers.resetMain()` so one test's dispatcher never leaks into the next.
## Junior notes
- `UnconfinedTestDispatcher` runs tasks eagerly, so ordering can differ from production dispatchers; prefer `StandardTestDispatcher` plus `runTest` when exact ordering matters.
- JUnit5-style `kotlin.test` annotations here (`@BeforeTest`/`@AfterTest`); JUnit4 tests use `@Rule MainDispatcherRule` for the same job. Do not apply both.
