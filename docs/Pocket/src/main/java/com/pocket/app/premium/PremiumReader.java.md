# Pocket/src/main/java/com/pocket/app/premium/PremiumReader.java
## What this is
This is the feature flag (a named on/off switch) for Premium reader features such as extra fonts and wider margins. It extends the app's `Feature` base class and answers one question: does the current user have Premium? `DisplaySettingsManager` consults it before applying premium fonts or margins.
## How it fits
Hilt DI (dependency injection: constructor parameters provided automatically, so you never `new` this yourself) creates it as a `@Singleton` with the current `AppMode` and `PocketCache`. `Feature.isEnabled()` callers (e.g. font preview revert, margin width) resolve through `isEnabled`, which currently just returns `pktcache.hasPremium()`.
## Key pieces
- `isEnabled(audience)` — the single gate: Premium on means premium reader on. The TODO notes it should one day check a finer-grained `PREMIUM_READER` flag (task P19-760) instead of blanket premium status.
## Junior notes
- `Feature` + `AppMode` is the app's gating pattern: `AppMode` distinguishes production vs internal builds, `Audience` distinguishes user segments — most flags combine both, this one currently only checks the cache.
