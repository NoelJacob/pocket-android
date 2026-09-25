# pocket-ui/src/main/java/com/pocket/ui/view/edittext/TextFinderView.kt

## What this is
The visible find-in-page bar: a thin strip with a search input, a match-count label ("3 of 12"), previous/next arrows, and a cancel button. The user types a query, sees where they are among matches, steps through them with the arrows, and dismisses the bar with cancel. The bar paints itself over the Pocket background color and captures taps so touches do not leak through to content beneath.

## How it fits
The concrete implementation of TextFinderLayout used by reader/webview screens for in-page search. It extends ThemedConstraintLayout (theme-aware ConstraintLayout) and inflates `ViewTextFinderBinding` (`view_text_finder` layout) into itself. Drivers get each part via the `root()` / `cancel()` / `input()` / `count()` / `back()` / `forward()` accessors and run the actual search; the `count` BindingAdapter (a static method letting XML set a property) lets databinding layouts push match text via `app:count="@{viewModel.matchText}"`.

## Key pieces
- `binding` (ViewTextFinderBinding): the inflated bar; `init` block sets the Pocket background and `isClickable = true` so the bar absorbs taps over underlying content.
- `root()` / `cancel()` / `input()` / `count()` / `back()` / `forward()`: TextFinderLayout implementation, each returning the matching bound view; WHY trivial accessors is drivers program against the interface, not this class.
- `setCountText(view, text)` companion BindingAdapter `"count"`: sets the count label from XML databinding; `@JvmStatic` exposes the Kotlin companion as a Java static for the databinding processor.

## Junior notes
- This view is dumb by design: it holds no search logic or match state; query handling and match stepping live in the hosting screen or ViewModel (which holds UI state and survives rotations).
- `isClickable = true` is load-bearing: without it, taps on empty bar padding fall through to the article or web content below.
- The databinding attribute is `app:count`, matching the adapter name, not the `count()` method; the two are linked only by this file.
