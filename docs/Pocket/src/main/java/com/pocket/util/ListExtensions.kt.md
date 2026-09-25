# Pocket/src/main/java/com/pocket/util/ListExtensions.kt

## What this is
A single helper, `containsAny`, that asks whether a list contains at least one of several candidate values. It solves the "does this filter set include any of these keys?" problem without chaining `||` calls. For example, `MyListViewModel` checks `state.filters.containsAny(ItemFilterKey.TAG, ItemFilterKey.NOT_TAGGED)` to decide whether the saves list is in a "tagged" filter state.

## How it fits
It sits in generic `com.pocket.util` and is consumed by filtering logic. Its known caller is `MyListViewModel`, which uses it in three places to map active filter keys to the displayed `SavesFilter` and to toggle filter-section visibility. It returns a plain Boolean to the caller.

## Key pieces
- `containsAny(vararg values: T)`: loops over the candidates and returns true on the first one the list contains. The `vararg` keyword lets callers pass a comma-separated list instead of building a collection.

## Junior notes
- It short-circuits on the first hit, so ordering cheap/more-likely matches first is marginally faster.
- This is the collection-side mirror of `AnyExtensions.equalsAny` (which tests one value against many); pick based on whether the many live in a list or in the argument list.
- Linear scan cost is fine for tiny filter lists; do not use it for large-collection membership tests where a `Set` lookup would be cheaper.
