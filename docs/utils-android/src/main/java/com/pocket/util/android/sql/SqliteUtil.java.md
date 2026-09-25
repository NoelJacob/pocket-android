# utils-android/src/main/java/com/pocket/util/android/sql/SqliteUtil.java

## What this is

This is a shared utility used across modules: a small, dependency-free helper that one feature needed and others reuse. It owns no app state and starts no work on its own.

## How it fits

Any module depending on `utils` (pure JVM) or `utils-android` (needs Android APIs) imports it directly. It is a leaf in the dependency graph: it must never depend back on feature code.

## Key pieces

- `SqliteUtil` (class, line 16) — Tools for working with SQLite, specifically Android's implementation.
- `tableExists` (fun, line 35) — @return true if this table exists.
- `tableSchema` (fun, line 43) — @return The schema of this table or null if it doesn't exist.
- `columnSchema` (fun, line 72) — @return the schema of this column within a table or null if the table or the column does not exist.
- `columnExists` (fun, line 85) — @return true if the column exists within the table. (returns false if the table does not exist).
- `placeholderList` (fun, line 89) — entry point other code calls; see callers for context.
- `toSelectArgsFromStrings` (fun, line 105) — Converts a collection of Strings to a String[] suitable for use in {@link SQLiteDatabase#rawQuery(String, String[])}
- `toSelectArgsFromObjects` (fun, line 112) — Same as {@link #toSelectArgsFromObjects(Collection)} but internally can avoid allocating an iterator since List can use an indexed for loop.
- `toSelectArgsFromObjects` (fun, line 126) — Converts a collection of Objects to a String[] suitable for use in {@link SQLiteDatabase#rawQuery(String, String[])}
- `getInteger` (fun, line 135) — entry point other code calls; see callers for context.

Concrete endpoints referenced here:

- `https://shipilev.net/blog/2016/arrays-wisdom-ancients/#_conclusion`
- `https://www.sqlite.org/limits.html`

## Junior notes

- Leaf helper with no app state: keep it dependency-free and never let it import feature code, or every module pays for the cycle.
