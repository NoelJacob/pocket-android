# Pocket/src/main/java/com/pocket/app/home/details/DetailsFragment.kt
## What this is
This is the abstract base screen for both details screens: the story list you see after tapping "See all" on a slate or a topic. It owns the shared layout (`FragmentHomeDetailsBinding`: app bar, scroll view, recycler, loading skeleton, book animation) and all the shared wiring. The two subclasses (`SlateDetailsFragment`, `TopicDetailsFragment`) only supply their ViewModel, their nav arguments, and how to open the reader.
## How it fits
Created via Navigation Component (the Jetpack library that moves between screens using a nav graph). `onViewCreated` sets up the recycler with a `DetailsAdapter`, observes one-shot navigation events from the ViewModel, and configures the app bar back button. `GoToReader` events delegate to the abstract `goToReader()`, `GoToSignIn` launches `AuthenticationActivity` (needed when an anonymous user tries to save), and `ShowRecommendationOverflow` shows `RecommendationOverflowBottomSheetFragment` as a child sheet.
## Key pieces
- `viewModel: DetailsViewModel` (abstract) — subclasses provide it via `by viewModels()` (a delegate that creates a Hilt-injected ViewModel scoped to the fragment; Hilt DI means constructor params are provided automatically).
- `setupRecyclerView()` — attaches the adapter, adds `VerticalSpacingDecorator`, uses a 2-column `GridLayoutManager` on tablets only, and sets `InstantChangeItemAnimator` so save-button toggles don't play a distracting change animation.
- `setupEventObserver()` — collects `viewModel.events` only while the fragment is RESUMED (`collectWhenResumed`), so navigation fires once and doesn't repeat after rotation.
- `setupBookAnimation()` — plays a Lottie-style footer animation when the user scrolls to the very bottom of the list.
- `onDestroyView()` — nulls `_binding`; required because fragments outlive their views and holding the binding would leak the whole view tree.
## Junior notes
- `showsDialog = false` in `onCreate` matters because `AbsPocketFragment` can act as a dialog; here it forces normal full-screen behavior.
- Back press is routed through `MainActivity.onBackPressed()`, not the NavController directly — don't add a second back handler here.
- `collectWhenResumed` vs `repeatOnCreated`: events (one-shot navigation) use RESUMED, state (lists) uses CREATED — mixing them up causes either missed events or duplicate navigation.

