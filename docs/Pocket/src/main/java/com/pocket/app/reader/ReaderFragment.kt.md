# Pocket/src/main/java/com/pocket/app/reader/ReaderFragment.kt
## What this is
This is the reader shell screen: a parent fragment (a reusable UI piece hosted inside an Activity) containing a nested navigation graph whose child is the Article, Collection, or Original Web view. It shows the previous/next bar at the bottom, owns the back-press behavior for the whole reader, and routes navigation events from the ViewModel to whichever child is currently visible. Users see one seamless reader; this is the frame around it.
## How it fits
Created by app navigation with `url` + queue args, it inflates `FragmentReaderBinding` (databinding: an XML layout bound to ViewModel fields so UI updates automatically), then posts `viewModel.onInitialized(...)` so the ViewModel can resolve the destination. `setupEventObserver` collects `navigationEvents` while resumed and delegates to the current child fragment; `openUrl`, `onPreviousClicked`, and `onNextClicked` are called by children (e.g. `ArticleFragment` tapping an article link) and forward into the ViewModel.
## Key pieces
- `navHostFragment / navController / currentFragment` — lookups into the nested child navigator; all screen swaps happen in this inner graph so the outer app back stack is untouched.
- `onBackPressed` — pops the inner reader stack first (and tells the ViewModel to pop its queue-manager stack), falling back to the outer navigation only when the reader has nowhere left to go. Returns true always because it consumes the press.
- `previousNextAnimator` — slides the prev/next bar in on every navigation event; children also poke it on scroll (see `ArticleFragment.setupScrollListener`).
## Junior notes
- `onInitialized` is posted to the main-thread `Handler` because the nested NavController is not ready synchronously in `onViewCreated` — calling it directly would drop the first navigation.
- `_binding` is nulled in `onDestroyView` (the standard view-binding lifecycle pattern); `collectWhenResumed` means navigation events only fire while the UI is visible, avoiding navigation crashes in the background.
