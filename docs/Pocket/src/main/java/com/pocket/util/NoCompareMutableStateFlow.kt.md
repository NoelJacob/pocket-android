# Pocket/src/main/java/com/pocket/util/NoCompareMutableStateFlow.kt

## What this is
A `StateFlow`-like holder (an observable stream of UI state) that emits every assigned value — even when the new value equals the old one. It solves the refresh problem with plain `MutableStateFlow`, which skips ("conflates") equal values: re-submitting an identical-looking list would silently drop the update. For example, `ListManager` holds its save list in one so that reloading the same items still pushes an update to the UI.

## How it fits
It is created and owned by `ListManager` (`_list`, exposed read-only as `NoCompareStateFlow`). Internally it wraps a `MutableSharedFlow` with `replay = 1` and `DROP_OLDEST` overflow so it behaves like state (always has a current value, new collectors get the latest). The `update` helper mirrors the `MutableStateFlow` API so call sites read the same as normal state code.

## Key pieces
- `NoCompareStateFlow`: read-only interface exposing `value` plus the `SharedFlow` contract. WHY it exists: lets owners expose state without exposing the setter.
- `NoCompareMutableStateFlow`: the implementation. Assigning `value` stores it and `tryEmit`s into the inner flow unconditionally. WHY it exists: the "always emit" guarantee plain `StateFlow` cannot give.
- `update(function)`: applies a transform to `value`. WHY it exists: API compatibility with `MutableStateFlow.update` so generic state-handling code works unchanged.

## Junior notes
- The file's own comment warns `update` here is NOT atomic: concurrent writers can lose updates, unlike the real `MutableStateFlow.update`. Only write from one coroutine/thread or add your own synchronization.
- `DROP_OLDEST` with `replay = 1` means a slow collector always sees the newest value, never a backlog — that is what makes it behave like state rather than an event queue.
- Reach for this only when equal values must still notify (refresh/re-submit); default to plain `StateFlow` everywhere else to avoid redundant UI work.
