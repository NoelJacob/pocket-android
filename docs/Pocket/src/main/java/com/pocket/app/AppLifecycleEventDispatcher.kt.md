# Pocket/src/main/java/com/pocket/app/AppLifecycleEventDispatcher.kt
## What this is
A Hilt singleton that keeps the set of `AppLifecycle` observers and fans events out to all of them. Components register themselves once; event sources (`App`, `UserManager`) then call `dispatch` with a small `Dispatch` object selecting which callback to fire. It holds no event logic itself, just the registry and the loop.
## How it fits
Sits between event producers and consumers: `App.onActivityChange`/`setUserPresent` and `UserManager.authenticate`/`logout` call `dispatch { component -> component.onXxx() }`, and every registered `AppLifecycle` (AppThreads, AppScope, UserManager itself, repositories) receives it. `AppOpen` is a typical self-registering consumer via its constructor.
## Key pieces
- `registerAppLifecycleObserver`: adds to the set and returns the observer for chaining; WHY a `Set` is that double-registration (e.g. constructor called twice in tests) stays harmless.
- `dispatch`: iterates all observers invoking the given `Dispatch`; null-safe on the argument so call sites can pass nullable dispatches.
- `Dispatch`: single-method interface whose `dispatch(appLifecycle)` body calls exactly one `AppLifecycle` callback; WHY this indirection instead of one method per event is that the dispatcher never needs updating when `AppLifecycle` gains a callback.
## Junior notes
- Registration normally happens in the component's `@Inject` constructor `init`, so ordering follows Hilt construction order; do not rely on dispatch order between observers.
- Exceptions thrown by one observer propagate out of `dispatch` and can skip later observers, so producers that must reach everyone (like `UserManager.authenticate`) wrap each call in try/catch.
