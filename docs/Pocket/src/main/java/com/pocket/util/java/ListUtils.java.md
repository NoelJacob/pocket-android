# Pocket/src/main/java/com/pocket/util/java/ListUtils.java
## What this is
Null-safe one-liners for collections: `isEmpty()` treats a null list as empty, `size()` treats null as 0, and `contains()` returns false when either side is null. They remove repetitive null guards before list checks.
For example, `ListUtils.size(items)` safely returns 0 when the list has not been loaded yet, instead of throwing a `NullPointerException`.

## How it fits
A leaf helper in `com.pocket.util.java` usable from anywhere lists may be absent (network results, optional fields, lazily loaded adapters). It has no dependencies, so it never pulls in app state.

## Key pieces
- `isEmpty(List)`: null-or-empty check. WHY it exists: lists are often nullable at boundaries, and this makes "nothing to show" a single call.
- `size(List)`: null-safe size. WHY it exists: avoids ternary noise like `list == null ? 0 : list.size()`.
- `contains(Collection, Object)`: null-safe membership test. WHY it exists: lets callers probe possibly-absent collections without guarding first.

## Junior notes
- `contains()` deliberately returns false when searching for `null`, so it cannot be used to detect a null entry in a collection.
- These only guard against `null`, not concurrent modification; do not iterate a list being changed on another thread.
