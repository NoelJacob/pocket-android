# Pocket/src/main/java/com/pocket/app/home/views/HomeErrorSnackBar.kt
## What this is
This is the swipe-to-dismiss error bar used on Home and details screens ("Couldn't load stories", Retry button, spinner while retrying). It extends `ThemedSwipeConstraintLayout`, which provides the error styling plus swipe-off-to-dismiss gesture. All behavior is exposed as databinding adapters so XML layouts drive it with plain ViewModel booleans and lambdas.
## How it fits
Details/topic screens bind this view's XML attributes (`isLoading`, `isShowing`, `onRetryClicked`, `title`, `message`, `onErrorSnackBarDismissed`, `allowSwiping`) to `DetailsViewModel.UiState` fields like `errorSnackBarVisible`/`errorSnackBarRefreshing`/`errorMessage`. Databinding (XML layouts bound to ViewModel fields) calls these `@BindingAdapter` static functions automatically when the bound values change. Retry invokes `onErrorRetryClicked()` (topic screen refetches); swiping invokes the dismissed callback.
## Key pieces
- `setLoading(isLoading)` — swaps progress spinner vs Retry button.
- `setShowing(isShowing)` — VISIBLE + `reset()` (restores swipe position) vs GONE; must reset because the view instance is reused.
- `setRetryClickedListener(onClick)` / `setTitle` / `setMessage` / `setAllowSwiping` — thin bridges from XML attributes to widgets.
- `setErrorSnackBarDismissed(onDismissed)` — installs a `SwipeListener` that fires on left or right swipe; `onMovement` is intentionally empty.
## Junior notes
- `@JvmStatic @BindingAdapter` functions must be static — that's why they live in `companion object`, not as instance methods.
- `isClickable = true; isFocusable = true` in `init` ensures taps on the bar don't fall through to the list underneath — removing them causes phantom card taps.

