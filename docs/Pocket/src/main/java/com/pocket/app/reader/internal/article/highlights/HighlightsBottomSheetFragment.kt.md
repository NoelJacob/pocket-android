# Pocket/src/main/java/com/pocket/app/reader/internal/article/highlights/HighlightsBottomSheetFragment.kt
## What this is
This is the slide-up panel listing all highlights for the current article, each with scroll-to, share, and delete actions. It is a bottom-sheet dialog fragment (a modal panel anchored to the bottom of the screen) bound to `HighlightsBottomSheetViewModel`, and it is how users review and manage highlights without leaving the reader.
## How it fits
`ArticleFragment.showHighlightOverlay` creates it via `newInstance(url, onHighlightClicked, onHighlightDeleted)` — note the callbacks are plain properties, not fragment arguments — and shows it in the `childFragmentManager`. Sheet events route outward: `Dismiss(scrollToId)` invokes the scroll callback and closes, `ShowShare` opens `ShareDialogFragment`, `RemoveHighlightFromWebView` invokes the delete callback so `ArticleViewModel` re-applies highlights in the WebView.
## Key pieces
- `newInstance` — constructor replacement that attaches the article URL plus the two lambdas the sheet needs to talk back to the article; exists because fragments must keep a no-arg constructor for rotation.
- `setupEventListener` — collects ViewModel events while resumed and translates each into a fragment action or parent callback; this is the sheet's only output path.
- `setupRecyclerView` — installs `HighlightSpacingDecorator` plus `HighlightsAdapter(viewLifecycleOwner, viewModel)` so the list renders and updates from the highlights flow.
## Junior notes
- The callbacks are lost on process death/rotation recreation (they are not in a Bundle), so this sheet does not survive configuration change the way argument-based fragments do — callers re-show it rather than relying on restoration.
- `_binding` is cleared in `onDestroyView`; the adapter's `repeatOnCreated` collection matches that lifecycle, so rotation drops and re-establishes observation cleanly.
