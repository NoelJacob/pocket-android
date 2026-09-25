# Pocket/src/main/java/com/pocket/app/reader/internal/article/highlights/HighlightsBottomSheet.kt
## What this is
This is the contract for the highlights bottom sheet (the slide-up panel listing a saved article's highlights): interaction interfaces plus the `Event` messages the ViewModel sends the fragment. It mirrors the `ArticleScreen`/`Reader` pattern of keeping screen vocabulary in one small file with no logic.
## How it fits
`HighlightsBottomSheetViewModel` implements `Initializer` and `HighlightInteractions` as rows are tapped; it emits `Event`s that `HighlightsBottomSheetFragment` handles by dismissing, opening `ShareDialogFragment`, or telling `ArticleViewModel` to refresh the in-article highlight rendering.
## Key pieces
- `Event.Dismiss(scrollToId?)` — closes the sheet, optionally scrolling the article to the tapped highlight; the optional id is what connects "tap a card" to "jump to that passage".
- `Event.ShowShare(text)` — opens sharing for one highlight quote without dismissing the sheet's data flow.
- `Event.RemoveHighlightFromWebView` — signals the article to re-render highlights after a delete (actual deletion happens in the repository first).
## Junior notes
- A Kotlin `object` with nested types (rather than a class) is used because this file is only a namespace — it is never instantiated.
