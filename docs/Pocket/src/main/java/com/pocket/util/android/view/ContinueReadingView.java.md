# Pocket/src/main/java/com/pocket/util/android/view/ContinueReadingView.java
## What this is
A swipe-dismissable "Continue Reading" banner shown at the top of the item list. It displays the Pocket icon, a "Continue reading" label, and the in-progress article's title, domain, and time-left estimate on an `ItemSnackbarView` card (a snackbar-style notification card).
## How it fits
Built by `com.pocket.app.list.MyListFragment` via `getInstance(context, item, continueReading, listener)`, which wires the click listener and the dismiss path: swiping it away removes the view (`ViewUtil.remove`) and logs the dismissal through `ContinueReading.trackDismiss()`. It reports analytics context (`CxtView.LIST` + `CxtUi.CONTINUE_READING`) via the `Contextual` interface.
## Key pieces
- `ContinueReadingView(context)` (private) — WHY: fixes card padding by subtracting card elevation from the standard spacing so the banner aligns with list cards.
- `getActionContext()` — WHY: tags analytics events so taps/dismissals attribute to the continue-reading surface.
- `getInstance(context, item, continueReading, listener)` — WHY: the only construction path; binds icon, title, domain, and time estimate through `ModelBindingHelper` (a formatter that turns an `Item` into display strings) and installs the swipe-to-dismiss behavior. Usage in words: pass the current item and a click listener to get a ready-to-show banner.
## Junior notes
- The constructor is private; always use `getInstance()`, never `new ContinueReadingView()`.
- Dismissal calls `ViewUtil.remove(continueReadingView)`, which detaches it from its parent; re-showing requires building a fresh instance.
- `ModelBindingHelper.title/item` value fields are preformatted display strings, not raw model fields; do not reformat them.

