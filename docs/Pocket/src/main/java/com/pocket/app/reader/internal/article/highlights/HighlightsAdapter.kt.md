# Pocket/src/main/java/com/pocket/app/reader/internal/article/highlights/HighlightsAdapter.kt
## What this is
This is the `RecyclerView` adapter (the object that turns a data list into visible rows) for the highlights bottom sheet: one card per highlight with tap-to-scroll, delete, and share actions. It is a `ListAdapter` wired to the ViewModel's highlight flow, so list updates animate automatically via diffing.
## How it fits
`HighlightsBottomSheetFragment` sets it on the highlight list; in `init` it collects `HighlightsBottomSheetViewModel.highlights` (a `StateFlow`, an observable state stream the UI subscribes to) while the view is created and submits each emission. Row taps call back into the same ViewModel (`onHighlightClicked`, `onDeleteClicked`, `onShareClicked`), which emits sheet-level events the fragment acts on.
## Key pieces
- `HighlightViewHolder.bind` — binds one `HighlightUiState(id, text)`: quote text plus three click targets (body scrolls to the highlight in the article, trash deletes, share opens the share sheet).
- `DIFF_CALLBACK` — compares rows by `id` for identity and full equality for contents; this is what makes insert/delete animations correct instead of redrawing the whole list.
## Junior notes
- `repeatOnCreated` collection in `init` means the adapter only observes while the fragment view exists — creating the adapter earlier or leaking it past `onDestroyView` would resubscribe or crash.
