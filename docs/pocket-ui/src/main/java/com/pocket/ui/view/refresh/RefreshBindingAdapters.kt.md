# pocket-ui/src/main/java/com/pocket/ui/view/refresh/RefreshBindingAdapters.kt
## What this is
A single databinding adapter (a function that lets an XML layout attribute drive a view property; databinding here means XML layouts bound to ViewModel fields) that connects a boolean ViewModel field to pull-to-refresh state. Setting `app:isRefreshing="@{viewModel.isLoading}"` in layout XML shows or hides the swipe spinner.

## How it fits
Any screen with a SwipeRefreshLayout (Android's pull-down-to-refresh container) binds its refreshing flag through this adapter instead of wiring listeners in code. The ViewModel exposes a plain boolean; when it changes, `setRefreshing` pushes it into `view.isRefreshing`. Refresh triggers still go through the layout's own listener; this adapter only reflects state.

## Key pieces
- `setRefreshing(view, isRefreshing)` — the `@BindingAdapter("isRefreshing")` entry point; assigns the platform `isRefreshing` property.

## Junior notes
- BindingAdapters must be top-level functions with the exact attribute name; the `app:` prefix in XML maps to the string here.
- One-way only: this pushes state to the view, it does not report user swipes back to the ViewModel.
