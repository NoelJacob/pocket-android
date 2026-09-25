# Pocket/src/main/java/com/pocket/util/java/Milliseconds.java
## What this is
Named time constants (`SECOND`, `MINUTE`, `HOUR`, `DAY`, `WEEK`, `YEAR` in millis) plus small converters between seconds, minutes, millis, and nanos. It replaces magic numbers like `20 * 60 * 1000` with readable arithmetic.
For example, `Milliseconds.MINUTE * 20` declares a 20-minute session timeout, and `toSeconds(millis)` converts a millis timestamp for a server that speaks seconds.

## How it fits
Used for expiry and stability windows: `AppSession` and `ItemSessions`/`Session` define `SESSION_EXPIRATION` from `MINUTE`, and `OfflineDownloading` checks `http.status().isStable(Milliseconds.MINUTE)` and compares `suspended + HOUR` against now. `WakefulTaskPool` formats idle time with `millisToMinutes()`.

## Key pieces
- `SECOND`/`MINUTE`/`HOUR`/`DAY`/`WEEK`/`YEAR`: millis multiples. WHY they exist: self-documenting durations without repeated `* 1000 * 60` math.
- `seconds(int)` / `minutesToMillis(int)` / `millisToMinutes(long)`: unit conversions. WHY they exist: keep call sites in their natural unit while storing millis.
- `since(long time)`: `System.currentTimeMillis() - time`. WHY it exists: one-liner "how long ago" for timestamps.
- `toSeconds(long millis)`: millis to whole seconds. WHY it exists: adapts client millis timestamps to second-based server APIs.
- `fromNanos(double nanos)`: nanos to millis. WHY it exists: converts high-resolution timers into the millis world.

## Junior notes
- These use wall-clock millis (`System.currentTimeMillis()`), which jumps if the user changes the clock; elapsed-time measurements should use `Clock.ELAPSED_REALTIME` instead.
- `YEAR = DAY * 365` ignores leap years and daylight saving; fine for rough expiry, wrong for calendar math.
