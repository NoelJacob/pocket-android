# Pocket/src/main/java/com/pocket/app/home/HomeFragment.kt
## What this is
The Home (Discover) tab screen: topics rail, recommendation slates, recent-saves rail, pull-to-refresh, and a celebratory book animation at the bottom. It binds `HomeViewModel` and `RecentSavesViewModel` via databinding (XML layouts bound to ViewModel fields), sets up the three RecyclerViews (Android's recyclable scrollable lists), and turns `Home.Event`s into navigation, bottom sheets, paywall, and sign-in flows.
## How it fits
Lives in `MainActivity`'s nav graph as the `home` destination. `onViewCreated` wires everything and calls both VMs' `onInitialized()`; `onResume` calls `onUserReturned()` for refresh-on-return. Events map to `HomeFragmentDirections` nav actions (reader, slate/topic details, saves), `RecommendationOverflowBottomSheetFragment` / `RecentSavesOverflowFragment` sheets, `premium.showPremiumForUserState`, and `AuthenticationActivity` for sign-in.
## Key pieces
- `setupRecyclerViews`: attaches `RecentSavesAdapter`, `SlatesAdapter` (tablet-aware), `TopicsAdapter`; saves list gets `InstantChangeItemAnimator` (no flicker on updates) and an observer that snaps to position 0 when new saves arrive at the top.
- `setupEventObserver` + `handleEvent`: collects both VMs' events while resumed (auto-pauses off-screen) and renders each `Home.Event`.
- `addSavesItemDecorator`: (re)adds the `HorizontalSpacingDecorator` with tablet edge compensation; WHY re-added on rotation with a 250ms delay is the new width is not measured yet during `onConfigurationChanged`, and stale decorators would double-space.
- `setupBookAnimation`: plays the footer animation once the scroll reaches its bottom.
- `setupSwipeRefreshListener`: pull gesture -> `viewModel.onSwipedToRefresh()`.
## Junior notes
- `_binding` is nulled in `onDestroyView`: the `Handler.postDelayed` in `addSavesItemDecorator` therefore guards with `_binding?.let`, a pattern to copy for any delayed work touching views.
- `showsDialog = false` in `onCreate` matters: this fragment class can act as a dialog elsewhere in the codebase, and Home must explicitly opt out or it renders floating.
