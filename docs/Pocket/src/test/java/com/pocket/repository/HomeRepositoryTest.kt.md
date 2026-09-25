# Pocket/src/test/java/com/pocket/repository/HomeRepositoryTest.kt
## What this is
A manually-run integration check for `HomeRepository` against production: logs in with test credentials, refreshes the lineup (Home feed sections) for US and German locales, and asserts caching flags plus first-slate titles ("For You" / "Empfohlene Artikel"). It is `@Ignore`d so CI never runs it.
## How it fits
Exercises the real `HomeRepository` over a real `Pocket` client (`PocketRemoteSource` plus OkHttp) with `Prefs(MemoryPrefStore())`. Not a unit test — a developer spot-check that the lineup endpoint and locale handling still behave, run from the IDE with breakpoints.
## Key pieces
- `hitsProd()` (`@Ignore`) — login, `refreshLineup`, `getLineup` Turbine assertions per locale; WHY: end-to-end sanity without launching the app.
- `hasCachedLineup` assertions — verify per-locale caching (refreshing German evicts US); WHY: pins the single-cached-lineup behavior.
- `pocket(debug)` helper — builds the networked client; WHY: shared setup for the manual run.
## Junior notes
- To run: comment out `@Ignore`, fill in test-account credentials, and expect titles to drift as Home content changes — update or drop those asserts freely.
- Turbine `awaitItem()` plus `cancelAndConsumeRemainingEvents()` verify exactly one lineup emission per locale with no extras.
