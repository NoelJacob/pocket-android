# Pocket/src/main/java/com/pocket/util/AnyExtensions.kt

## What this is
A single generic helper, `equalsAny`, that checks whether a value equals any one of several candidates. It solves the "is this state one of these three error states?" problem without chaining `||` comparisons. For example, calling `loadState.equalsAny(ERROR_A, ERROR_B)` reads as plain English and returns true if the state matches either one.

## How it fits
It lives in the generic `com.pocket.util` package and is used by app code that branches on state. Its known caller is `ListManager`, which tests a `DataSourceCache.LoadState` against several error variants to decide how to handle a My List load failure. Nothing downstream is produced; it just returns a Boolean to the caller's `if`.

## Key pieces
- `equalsAny(vararg values: T)`: the only symbol here. It loops over the candidates and returns true on the first structural (`==`) match, false otherwise. The `vararg` keyword means callers pass a comma-separated list instead of building an array.

## Junior notes
- `==` in Kotlin is structural equality (calls `equals`), not reference identity (`===`), so this compares values.
- It short-circuits: it stops checking at the first match, like `||` would.
- See also `ListExtensions.containsAny`, the mirror helper for asking whether a list contains any of several values.
