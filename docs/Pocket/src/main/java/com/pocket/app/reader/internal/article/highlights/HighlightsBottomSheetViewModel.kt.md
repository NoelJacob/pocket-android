# Pocket/src/main/java/com/pocket/app/reader/internal/article/highlights/HighlightsBottomSheetViewModel.kt
## What this is
This is the ViewModel behind the highlights sheet: it streams the article's highlights from `HighlightRepository`, exposes them as `HighlightUiState(id, text)` rows sorted by position in the article, and handles row taps (scroll-to, share, delete). It owns the list content while the fragment owns presentation.
## How it fits
Initialized with the article URL from the fragment, it collects `highlightRepository.getHighlightsFlow(url)` (a continuous stream, so edits elsewhere update the sheet live) and maps each highlight to row state. Clicks emit `HighlightsBottomSheet.Event`s the fragment executes; deletes go through the repository first, then emit `RemoveHighlightFromWebView` so the article re-renders, plus `Dismiss` when the last highlight is gone.
## Key pieces
- `setupHighlightsObserver` — sorts highlights by the offset parsed out of the patch string (`"@@ -857,16 ..."` → 857) so the sheet follows article order, not creation order. Fragile but intentional: patch position is the only location signal.
- `onDeleteClicked` — captures the pre-delete count, deletes via repository, notifies the WebView refresh, and auto-dismisses only when the list is now empty — deleting one of many keeps the sheet open.
- `onHighlightClicked / onShareClicked` — pure event emitters; navigation and sharing stay in the fragment, keeping this ViewModel free of Android UI classes.
## Junior notes
- The patch-substring sort uses `toIntOrNull` with a safe fallback, so a malformed patch degrades to unsorted rather than crashing the sheet.
- `viewModelScope` (a coroutine scope tied to the ViewModel's lifetime) is used for both collecting and deleting — work cancels automatically when the sheet is destroyed.
