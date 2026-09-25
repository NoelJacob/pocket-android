# sync-android/src/main/java/com/pocket/sync/source/AndroidAsyncClientSourceExtensions.kt

## What this is

Android-flavored suspending wrappers for AppSource calls: like the core AsyncClientSourceExtensions it proxies call sequences through one shared PendingResult, but with a default Publisher that delivers on Android's main thread. Coroutines (Kotlin's pausable background tasks) plus a main-thread default mean ViewModels get straight-line code that still updates UI safely. A PendingResult is the engine's async callback handle underneath.

## How it fits

Kotlin ViewModels and repositories use these instead of raw callbacks when driving AppSource from the UI layer. It layers the Android default publisher over the core suspending-proxy mechanism; engine semantics are unchanged.

## Key pieces

- `suspending AppSource wrappers` — straight-line coroutine calls sharing one PendingResult across the proxied sequence
- `Android default publisher` — main-thread delivery without every call site specifying a Publisher

## Junior notes

- Main-thread delivery is a default, not a rule: heavy mapping work should still hop to a background dispatcher before returning to the UI.
