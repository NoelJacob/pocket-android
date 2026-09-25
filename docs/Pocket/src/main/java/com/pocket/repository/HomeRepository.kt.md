# Pocket/src/main/java/com/pocket/repository/HomeRepository.kt
## What this is
Provides the Home feed: a list of slates (themed rows), each with up to 10 recommendation cards. It caches the lineup per locale (language/region), refreshes it from the server, and exposes it as a Flow for the UI.
## How it fits
Created once via Hilt DI (constructor parameters provided automatically) and used by the Home screen/ViewModel. It builds a `Home` query through the sync engine (`Pocket`), pins it in a persistent `Holder` (a disk-cached bucket surviving restarts), and maps server slates to `DomainSlate`/`DomainRecommendation`. This is the read side of sync: `syncRemote` pulls server state into the local cache; `bindLocalAsFlow` observes the local copy.
## Key pieces
- `holder` (`Holder.persistent("home-8.26.1")`) — versioned disk cache key; WHY versioned: `bustCachePre826()` drops the pre-8.26 cache whose items lack the now-required `preview` field.
- `currentLocale` — persisted per-user preference; changing locale forgets the old lineup and remembers the new one so feeds don't mix languages.
- `refreshLineup(locale)` — re-points the cache on locale change, then `syncRemote(...).await()` to fetch fresh slates.
- `getLineup(locale)` — live Flow of `DomainSlate` lists from the local cache; `hasCachedLineup()` is the one-shot "do we have anything to show offline?" check.
- `toDomainSlate()` / `toRecommendation(index)` — map server slates to UI cards; `index` is stamped per card for analytics ordering.
## Junior notes
- The holder name is the cache-busting mechanism: bumping `"home-8.26.1"` invalidates every stored lineup, so don't rename it casually.
- `isSaved` here checks `savedItem?.item != null`, a different shape than the `status == UNREAD/ARCHIVED` check elsewhere; both mean "user saved this".
