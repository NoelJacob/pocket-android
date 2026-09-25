# Pocket/src/main/java/com/pocket/app/home/Home.kt
## What this is
The contract file for the Home (Discover) tab: interaction interfaces the UI talks to (`Interactions`, `RecommendationInteractions`, `SavesInteractions`, `ErrorSnackBarInteractions`) plus the one-shot `Event` set the ViewModels emit for navigation (`GoToReader`, overflow sheets, slate/topic details, My List, Premium, sign-in). No logic lives here; it is the shared vocabulary keeping adapters, fragments, and viewmodels decoupled.
## How it fits
`HomeFragment` wires adapters (`SlatesAdapter`, `TopicsAdapter`, `RecentSavesAdapter`) to `HomeViewModel`/`RecentSavesViewModel` through these interfaces; user taps become interface calls, which become `Event`s, which the fragment renders as nav actions, bottom sheets, paywall, or sign-in. `HomeViewModel` implements all four interfaces.
## Key pieces
- `Interactions` (init/return/topic/refresh/upsell): tab-level inputs from fragment lifecycle and header buttons.
- `RecommendationInteractions` (click/save/overflow/see-all/viewed): per-recommendation inputs carrying slate title, position, and corpus ID for analytics attribution.
- `SavesInteractions`: recent-saves rail inputs carrying full `Item`s plus positions.
- `ErrorSnackBarInteractions`: retry/dismiss for the offline error bar.
- `Event` sealed set: WHY one closed type is the fragment's `when` stays exhaustive, so new destinations force handling at compile time.
## Junior notes
- Corpus recommendation IDs ride along on click/save/viewed events for recommendation analytics; pass them through untouched even if your change does not need them.
- `onRecommendationViewed` / `onSignInBannerViewed` are currently no-op impression hooks: implement measurement there when impression analytics is specced, rather than inventing parallel callbacks.
